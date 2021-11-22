aws stepfunctions update-state-machine 
--state-machine-arn $STATE_MACHINE_ARG \
--definition file://../simple-math.asl.json \
--role-arn $STATE_MACHINE_ROLE \
--profile $AWS_PROFILE
