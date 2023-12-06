COMMIT_MSG_FILE=$1
ISSUE_NUMBER=$(get_issue_number) # 이슈 번호를 얻는 로직을 작성해야 합니다.

COMMIT_MSG=$(cat $COMMIT_MSG_FILE)

echo "#${ISSUE_NUMBER} ${COMMIT_MSG}" > $COMMIT_MSG_FILE

get_issue_number() {
  BRANCH_NAME=$(get_git_branch_name)
  ISSUE_NUMBER=$(echo "$BRANCH_NAME" | sed -n 's/.*\/\([0-9]*\)-.*/\1/p')
  echo "$ISSUE_NUMBER"
}

get_git_branch_name() {
  git symbolic-ref --short HEAD
}