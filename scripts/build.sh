#!/usr/bin/env bash
set -e

cd "$(dirname "$0")/.."

SRC_DIR="src"
LIB_DIR="lib"
JAR_NAME="app.jar"
BUILD_DIR="build"

KOTLINX_CLI_JAR=$(ls "$LIB_DIR"/kotlinx-cli-*.jar | head -n1)
H2_JAR=$(ls "$LIB_DIR"/h2-*.jar | head -n1)
KOTLIN_STDLIB=$(ls "$LIB_DIR"/kotlin-stdlib-*.jar | head -n1)

if [ -z "$KOTLINX_CLI_JAR" ] || [ -z "$H2_JAR" ]; then
  echo "ОШИБКА: Не найдены библиотеки в lib/"
  exit 1
fi

CP="${KOTLINX_CLI_JAR}:${H2_JAR}"

echo "Компиляция исходников..."
rm -rf "${BUILD_DIR}"
mkdir -p "${BUILD_DIR}/classes"
rm -f "${JAR_NAME}"

kotlinc "${SRC_DIR}" \
  -cp "${CP}" \
  -include-runtime \
  -d "${BUILD_DIR}/app-with-runtime.jar"

echo "Сборка Fat JAR (упаковка зависимостей)..."
cd "${BUILD_DIR}/classes"
jar xf "../app-with-runtime.jar"
jar xf "../../${KOTLINX_CLI_JAR}"
jar xf "../../${H2_JAR}"

cd - > /dev/null

cat > "${BUILD_DIR}/MANIFEST.MF" <<EOF
Main-Class: MainKt
EOF

jar cfm "${JAR_NAME}" "${BUILD_DIR}/MANIFEST.MF" -C "${BUILD_DIR}/classes" .

rm -rf "${BUILD_DIR}"

echo "Сборка завершена: ${JAR_NAME}"
