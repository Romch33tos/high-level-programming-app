#!/usr/bin/env bash
set -e

cd "$(dirname "$0")/.."

LIB_DIR="lib"
OUT_DIR="out_test"

JUNIT_STANDALONE=$(ls "$LIB_DIR"/junit-platform-console-standalone-*.jar | head -n1)
KOTLINX_CLI_JAR=$(ls "$LIB_DIR"/kotlinx-cli-*.jar | head -n1)
H2_JAR=$(ls "$LIB_DIR"/h2-*.jar | head -n1)
KOTLIN_STDLIB=$(ls "$LIB_DIR"/kotlin-stdlib-*.jar | head -n1)

if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" || "$OSTYPE" == "win32" ]]; then
  SEP=";"
else
  SEP=":"
fi

CP="${OUT_DIR}${SEP}${KOTLINX_CLI_JAR}${SEP}${KOTLIN_STDLIB}${SEP}${H2_JAR}"

if [ ! -d "$OUT_DIR" ]; then
  echo "Сначала выполните ./scripts/build_tests.sh"
  exit 1
fi

echo "Запуск модульных тестов JUnit..."
java -jar "$JUNIT_STANDALONE" \
  --class-path "$CP" \
  --scan-class-path
