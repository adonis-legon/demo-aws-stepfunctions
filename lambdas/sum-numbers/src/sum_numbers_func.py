def lambda_handler(payload, context):
    payload['result'] = {
        'sum': payload['a'] + payload['b']
    }

    return payload
