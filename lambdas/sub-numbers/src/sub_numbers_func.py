def lambda_handler(payload, context):
    payload['result']['sub'] = payload['result']['sum'] - payload['c']

    return payload
