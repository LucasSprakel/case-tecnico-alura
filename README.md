# case-tecnico-alura
Case técnico Alura

# Inicio
Configure o banco de dados em application.properties com as suas credencias SQL:
![configsql](https://github.com/LucasSprakel/case-tecnico-alura/assets/53552116/e155f93a-4c9b-4aaa-ba2e-b6f990013c80)

# Endpoints
**POST user:**

Endpoint("/user")

Atributos do BODY:
- name
- username (apenas caracteres minúsculos, sem numerais e sem espaços)
- email (endereço de email em formato válido)
- password
- role (Só pode ser "STUDENT", "INSTRUCTOR" ou "ADMIN")

![postUser](https://github.com/LucasSprakel/case-tecnico-alura/assets/53552116/d8fe2076-fe12-42a4-911b-d778c86862fe)

**GET todos os users:**

Endpoint("/users")

![getAllUsers](https://github.com/LucasSprakel/case-tecnico-alura/assets/53552116/94d2c015-791c-41e6-bd97-c7b1c80f5960)

**GET user especifico:**

Endpoint("/user/{username}")

Response:
- name
- email
- role

![getUser](https://github.com/LucasSprakel/case-tecnico-alura/assets/53552116/2a921585-43d5-4091-b99a-5d5a3c46e23c)






