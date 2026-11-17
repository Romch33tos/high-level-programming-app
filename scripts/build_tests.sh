#!/usr/bin/env bash
set -e

cd "$(dirname "$0")/.."

SRC_DIR="src"
TEST_DIR="test"
LIB_DIR="lib"
OUT_DIR="out_test"

JUNIT_STANDALONE=$(ls "$LIB_DIR"/junit-platform-console-standalone-*.jar | head -n1)
KOTLINX_CLI_JAR=$(ls "$LIB_DIR"/kotlinx-cli-*.jar | head -n1)
H2_JAR=$(ls "$LIB_DIR"/h2-*.jar | head -n1)

if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" || "$OSTYPE" == "win32" ]]; then
  SEP=";"
else
  SEP=":"
fi

CP="${JUNIT_STANDALONE}${SEP}${KOTLINX_CLI_JAR}${SEP}${H2_JAR}"

echo "Компиляция тестов..."

rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

kotlinc \
  -cp "$CP" \
  -include-runtime \
  -d "$OUT_DIR" \
  $(find "$SRC_DIR" "$TEST_DIR" -name '*.kt' ! -name 'CliRequestMapper.kt' ! -name 'Main.kt')

echo "Компиляция тестов завершена. Классы в $OUT_DIR"
