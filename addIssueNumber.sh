COMMIT_MSG_FILE=$1
ISSUE_NUMBER=$(get_issue_number) # 이슈 번호를 얻는 로직을 작성해야 합니다.

COMMIT_MSG=$(cat $COMMIT_MSG_FILE)

echo "#${ISSUE_NUMBER} ${COMMIT_MSG}" > $COMMIT_MSG_FILE

get_issue_number() {
  # 이슈 번호를 얻는 로직을 작성합니다.
  # 예시로는 git branch 이름에서 이슈 번호를 추출하는 방법을 사용할 수 있습니다.
  BRANCH_NAME=$(get_git_branch_name)
  ISSUE_NUMBER=$(expr "$BRANCH_NAME" : '.*#\([0-9]*\)')
  echo $ISSUE_NUMBER
}

get_git_branch_name() {
  git symbolic-ref --short HEAD
}