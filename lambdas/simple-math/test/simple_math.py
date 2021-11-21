from src.simple_math_func import lambda_handler

import json
import os
from pathlib import Path

if __name__ == '__main__':
    sample_event_file = os.path.join(Path(__file__).parent, 'event.json')

    with open(sample_event_file) as f:
        event = json.load(f)
        print(lambda_handler(event, None))