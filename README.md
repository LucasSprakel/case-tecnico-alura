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
- username (20 caracteres no máximo)(apenas caracteres minúsculos, sem numerais e sem espaços)
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

<br>
<br>

**POST course:**

Endpoint("/course")

Atributos do BODY:
- name
- code (10 caracteres no máximo)(deve ser textual, sem espaços, nem caracteres numéricos e nem caracteres especiais, mas pode ser separado por - , exemplo: alura-java)
- instructor (Deve ser um username cadastrado com role "INSTRUCTOR")
- description

![postCourse](https://github.com/LucasSprakel/case-tecnico-alura/assets/53552116/a847dbdd-bd27-4b59-a389-c9eb573b3a0e)


**GET course by Status:**

Endpoint("/courses")

Atributos não obrigatórios do Path:
- status
- page (Default: 0)
- size (default: 10)

Endpoint com atributos("/courses?status=Active&page=0&size=1")
Nesse exemplo é retornado apenas os cursos Ativos da primeira página, cada página contendo apenas um curso

![getCoursebyStatus](https://github.com/LucasSprakel/case-tecnico-alura/assets/53552116/8deaafbb-f99c-44af-80b0-5ea50fd879a3)


**PUT desativar course:**

Endpoint("/course/{code}/disable")

Atributos obrigatórios do Path:
- code (Código de um curso já criado)

![putDesativarCourse](https://github.com/LucasSprakel/case-tecnico-alura/assets/53552116/e0d0a431-ee2b-4a9f-a7d0-b68237be3f9d)


<br>
<br>

**POST registration:**

Endpoint("/registration")

Atributos do BODY:
- username (username cadastrado)
- courseCode (codigo de curso cadastrado e ativado)

![postRegistration](https://github.com/LucasSprakel/case-tecnico-alura/assets/53552116/fdb27ab4-77b7-4637-a5cb-0cef1d080866)

**GET todos os registrations:**

Endpoint("/registrations")

![getRegistrations](https://github.com/LucasSprakel/case-tecnico-alura/assets/53552116/aa6f3b85-f116-49d5-94ff-e261bc7af09e)



<br>
<br>

**POST feedback:**

Endpoint("/feedback")

Atributos do BODY:
- username (username cadastrado)
- courseCode (codigo de curso cadastrado e ativado)
- rating (valores entre 0 e 10)
- feedback

É verificado se o usuario já possui um feedback feito nesse mesmo curso.
Envia um email para o instrutor caso o rating seja menor que 6

![postFeedback](https://github.com/LucasSprakel/case-tecnico-alura/assets/53552116/e4fcf7ae-e8fb-4b06-b7eb-373d91d2ddb2)


**GET todos os feedbacks:**

Endpoint("/feedbacks")

![getFeedbacks](https://github.com/LucasSprakel/case-tecnico-alura/assets/53552116/8d0763b7-d1f4-4d06-8d45-1f62b89c8edb)



**PUT feedback:**

Endpoint("/feedback")

Atributos do BODY:
- username (username cadastrado)
- courseCode (codigo de curso cadastrado e ativado)
- rating (valores entre 0 e 10)
- feedback

Envia um email para o instrutor caso o rating seja menor que 6

![putFeedback](https://github.com/LucasSprakel/case-tecnico-alura/assets/53552116/c71d9ac8-2023-4a0f-bffe-f56dafd228ea)


**GET relatorio NPS:**

Endpoint("/report")
Mostra o NET PROMOTER SCORE de cursos que possuem pelo menos 4 matriculas

![getRelatorioNPS](https://github.com/LucasSprakel/case-tecnico-alura/assets/53552116/d2a03912-55c1-4c48-ad39-2c3aa19f1892)

























