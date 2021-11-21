aws stepfunctions update-state-machine \
--state-machine-arn arn:aws:states:sa-east-1:342542966819:stateMachine:simple-math \
--definition file://../simple-math.asl.json \
--role-arn arn:aws:iam::342542966819:role/service-role/StepFunctions-simple-math-role-48ab458e \
--profile sovos-gvat-dev
