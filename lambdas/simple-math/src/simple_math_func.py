import json
import boto3
import os


def lambda_handler(event, context):
    response_code = 200
    body = {
        'message': ''
    }

    if not event['body']:
        response_code = 400
        body['message'] = 'Missing Workflow arguments on Request Body'
    else:
        try:
            # invoke step function with body arguments
            if os.environ.get('WF_ACCESS_KEY') and os.environ.get('WF_SECRET_KEY') and os.environ.get('WF_REGION'):
                wf_client = boto3.client(
                    'stepfunctions',
                    aws_access_key_id=os.environ['WF_ACCESS_KEY'],
                    aws_secret_access_key=os.environ['WF_SECRET_KEY'],
                    region_name=os.environ['WF_REGION']
                )
            else:
                wf_client = boto3.client('stepfunctions')

            if event['headers'] and 'x-simplemath-async' in event['headers'].keys() \
                and event['headers']['x-simplemath-async'].lower() == 'true':
                wf_response = wf_client.start_execution(
                    stateMachineArn=os.environ['WF_ARN'],
                    name=os.environ['WF_NAME'],
                    input=event['body']
                )
            else:
                wf_response = wf_client.start_sync_execution(
                    stateMachineArn=os.environ['WF_ARN'],
                    name=os.environ['WF_NAME'],
                    input=event['body']
                )
                body['result'] = json.loads(wf_response['output'])['result']

            body['message'] = 'Success'
        except Exception as ex:
            response_code = 500
            body['message'] = 'Error invoking Workflow. Message: ' + ex.__str__()
    return {
        "statusCode": response_code,
        "headers": {
            "Content-Type": "application/json"
        },
        "body": json.dumps(body)
    }
