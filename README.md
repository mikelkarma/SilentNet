# Sistema de Comunicação Anônima com Criptografia Avançada

O objetivo deste sistema é criar uma plataforma de comunicação anônima e segura, permitindo que os clientes se comuniquem entre si e compartilhem informações de forma confidencial e sem riscos de interceptação. O sistema será composto por dois componentes principais: o **Cliente** e o **Cliente Master**.

## Arquitetura do Sistema

### Cliente

Cada cliente do sistema terá uma porta aberta (porta 12137) para comunicação com outros clientes. Além disso, cada cliente terá um **ID único** de identificação. As comunicações entre os clientes serão criptografadas para garantir a confidencialidade das informações trocadas.

Para evitar a repetição de informações, cada dado transmitido terá uma **licença única**. Caso um cliente não possua a licença associada à informação, ele simplesmente rejeitará a mensagem. Assim, evita-se que informações sejam duplicadas. As mensagens serão criptografadas utilizando um esquema de criptografia exclusivo, onde somente o **operador** do sistema terá a chave para **descriptografar** a informação original. Os clientes, embora recebam as informações, não terão a capacidade de descriptografá-las, apenas de semear ou propagar os dados.

### Criptografia e Assinaturas

Para garantir a segurança das comunicações, será adotado um sistema híbrido que combina **AES** para criptografia eficiente de dados e **RSA** para criptografia de chaves, além de **assinaturas digitais** para autenticar a origem das mensagens.

#### Geração de Chaves

- O **Cliente** gera sua chave pública e privada utilizando **RSA**.
- O **Cliente** também gera uma chave **AES** para criptografar a comunicação com o Cliente Master.

#### Fluxo de Criptografia:

1. **Criptografia da Chave Pública do Cliente:**
   - O **Cliente** criptografa sua **chave pública** com a chave **AES** gerada.
   - Isso garante que, mesmo que a chave pública do cliente seja interceptada, ela não pode ser lida sem a chave AES correspondente.

2. **Criptografia da Chave AES com a Chave Pública do Cliente Master:**
   - A chave **AES** gerada pelo **Cliente** é criptografada com a **chave pública do Cliente Master** (RSA).
   - Assim, somente o **Cliente Master** poderá descriptografar a chave AES com sua chave privada correspondente.

3. **Envio de Dados:**
   - O **Cliente** envia a chave pública criptografada com AES e a chave AES criptografada com a chave pública do Cliente Master para o Cliente Master.

4. **Descriptografia no Cliente Master:**
   - O **Cliente Master** usa sua chave privada RSA para **descriptografar a chave AES**.
   - Uma vez que o Cliente Master tenha a chave AES, ele pode usá-la para **descriptografar a chave pública** do **Cliente**, permitindo que a comunicação segura seja estabelecida.

5. **Comunicação Segura entre o Cliente e o Cliente Master:**
   - Com as chaves agora protegidas, o **Cliente Master** pode usar a chave pública do cliente (descriptografada) para criptografar mensagens para o **Cliente**, e o **Cliente** pode usar sua chave privada para descriptografá-las.

### Cliente Master

O **Cliente Master** será responsável por enviar comandos criptografados aos clientes. Cada comando será associado ao **ID do destinatário**, e somente esse destinatário será capaz de descriptografá-lo. Os semeadores (clientes intermediários) repassarão o comando criptografado entre si até que o destinatário correto seja encontrado. Isso garante que os dados permaneçam protegidos durante todo o processo de transmissão.

#### Criptografia e Assinatura dos Comandos pelo Cliente Master

Além disso, os comandos enviados pelo Cliente Master serão protegidos para garantir que não possam ser falsificados ou alterados durante a transmissão. Isso é feito por meio de **assinaturas digitais**.

1. **Assinatura Digital do Comando:**
   - O **Cliente Master** gera o comando a ser enviado e **assina digitalmente o comando** com sua **chave privada**. Essa assinatura garante que somente o **Cliente Master** pode ter assinado aquele comando, e qualquer alteração subsequente na mensagem invalidará a assinatura.
   
2. **Verificação da Assinatura:**
   - Quando um **Cliente** recebe o comando, ele verifica a assinatura digital usando a **chave pública** do **Cliente Master**. Isso garante que o comando não foi alterado e que ele foi realmente emitido pelo Cliente Master.

3. **Criptografia do Comando (Opcional):**
   - Caso o comando contenha informações sensíveis, ele pode ser criptografado com a **chave pública** do destinatário (cliente específico) ou com a **chave pública** do Cliente Master para garantir que ninguém, exceto o destinatário autorizado, possa ler ou alterar o conteúdo do comando.

#### Fluxo do Cliente Master

- **Passo 1**: O Cliente Master gera um comando.
- **Passo 2**: O Cliente Master assina digitalmente o comando com sua chave privada.
- **Passo 3**: O Cliente Master envia o comando assinado e criptografado para os clientes.
- **Passo 4**: O Cliente verifica a assinatura digital do comando usando a chave pública do Cliente Master.
   - Se a assinatura for válida, o Cliente sabe que o comando é autêntico e não foi alterado.
   - Se a assinatura for inválida, o Cliente rejeita o comando.
- **Passo 5**: O Cliente pode então processar o comando conforme necessário.

## Proteção Contra Falsificação e Interceptação

- **Autenticidade**: A assinatura digital garante que o comando foi enviado pelo Cliente Master e que não foi falsificado.
- **Integridade**: A assinatura também garante que o comando não foi alterado durante o transporte. Se alguém modificar o comando após ele ser assinado, a verificação da assinatura falhará.
- **Confidencialidade**: Se o comando for criptografado, apenas o destinatário correto poderá lê-lo, garantindo que a informação não seja interceptada.
- **Segurança na Troca de Chaves**: A chave AES usada para criptografar as mensagens é protegida pelo algoritmo RSA, garantindo que apenas o Cliente Master tenha a chave privada necessária para descriptografá-la.

## O Que o Cliente Faz na Hora de Enviar e Receber Dados

### Envio de Dados

1. **Preparação da Mensagem**:
   - O **Cliente** gera uma mensagem para enviar a outro cliente ou ao Cliente Master.
   - A mensagem é primeiro criptografada com a chave AES gerada pelo cliente.
   - A chave pública do destinatário (ou do Cliente Master) é utilizada para criptografar a chave AES.

2. **Envio da Mensagem Criptografada**:
   - A mensagem criptografada, junto com a chave AES criptografada, é enviada para o destinatário.
   - O **Cliente** usa a porta de comunicação 12137 para fazer a conexão e enviar os dados para o destinatário ou Cliente Master.

3. **Comunicação com Outros Clientes**:
   - Se o Cliente não é o destinatário final, ele repassará a mensagem para outros clientes, garantindo que ela seja entregue ao destinatário final, sem modificar o conteúdo.

### Recebimento de Dados

1. **Recepção da Mensagem Criptografada**:
   - O **Cliente** recebe uma mensagem criptografada e verifica se a mensagem é para ele, conferindo o ID do destinatário.
   - Se for a mensagem destinada ao Cliente, ele prossegue para o próximo passo.

2. **Descriptografando a Chave AES**:
   - O **Cliente** usa sua chave privada para **descriptografar** a chave AES (caso ela tenha sido criptografada com a chave pública do Cliente).
   
3. **Descriptografando a Mensagem**:
   - Após obter a chave AES, o **Cliente** a usa para **descriptografar** a mensagem original que foi enviada.

4. **Processamento da Mensagem**:
   - O **Cliente** processa a mensagem, realizando a ação correspondente de acordo com os dados recebidos.

## O Que o Cliente Master Faz na Hora de Enviar e Receber Dados

### Envio de Comandos

1. **Criação do Comando**:
   - O **Cliente Master** gera um comando (mensagem ou instrução) para ser enviado a um ou mais clientes.
   
2. **Assinatura e Criptografia**:
   - O **Cliente Master** assina digitalmente o comando com sua chave privada.
   - Se o comando contiver dados sensíveis, ele é criptografado com a chave pública do destinatário ou do Cliente Master.

3. **Envio do Comando**:
   - O **Cliente Master** envia o comando assinado e criptografado para os clientes, garantindo que apenas o destinatário correto possa processá-lo.

### Recebimento de Comandos

1. **Recepção do Comando**:
   - O **Cliente Master** recebe um comando de outro cliente e verifica a assinatura digital usando a chave pública do cliente remetente.

2. **Descriptografia do Comando**:
   - Se a assinatura for válida, o **Cliente Master** descriptografa a mensagem (se criptografada) e processa a informação.

## Conclusão

O sistema proposto garante a segurança, confidencialidade e integridade das mensagens e comandos enviados entre os clientes e o Cliente Master, utilizando criptografia avançada e assinaturas digitais.
