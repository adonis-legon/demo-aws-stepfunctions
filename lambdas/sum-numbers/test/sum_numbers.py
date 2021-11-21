from src.sum_numbers_func import lambda_handler

import json
import os
from pathlib import Path

if __name__ == '__main__':
    sample_payload_file = os.path.join(Path(__file__).parent, 'payload.json')

    with open(sample_payload_file) as f:
        payload = json.load(f)
        print(lambda_handler(payload, None))