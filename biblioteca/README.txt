House of Letters - Sistema de Biblioteca (Java)

Compilar (na pasta biblioteca):
  javac -encoding UTF-8 -d out $(find src -name "*.java")      (Linux/Mac)
  javac -encoding UTF-8 -d out src\aplicacao\*.java src\entidades\*.java src\enumeracoes\*.java src\interfaces\*.java src\repositorios\*.java src\servicos\*.java src\util\*.java   (Windows)
Executar:
  java -cp out aplicacao.Programa
