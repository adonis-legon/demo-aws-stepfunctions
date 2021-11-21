def lambda_handler(payload, context):
    payload['result']['div'] = payload['result']['sub'] / payload['d']

    return payload
