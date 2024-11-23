---

**Construção de um Sistema de Comunicação Anônima com Python, C, Java e talvez Tor**

O objetivo deste sistema é criar uma plataforma de comunicação anônima e segura, permitindo que os clientes se comuniquem entre si e compartilhem informações de forma confidencial e sem riscos de interceptação. O sistema será composto por dois componentes principais: o **Cliente** e o **Cliente Master**.

### **Cliente**

Cada cliente do sistema terá uma porta aberta (porta 12137) para comunicação com outros clientes. Além disso, cada cliente terá um **ID único** de identificação. As comunicações entre os clientes serão criptografadas para garantir a confidencialidade das informações trocadas.

Para evitar a repetição de informações, cada dado transmitido terá uma **licença única**. Caso um cliente não possua a licença associada à informação, ele simplesmente rejeitará a mensagem. Assim, evita-se que informações dubuplicadas. As mensagens serão criptografadas utilizando um esquema de criptografia exclusivo, onde somente o **operador** do sistema terá a chave para **descriptografar** a informação original. Os clientes, embora recebam as informações, não terão a capacidade de descriptografá-las, apenas de semear ou propagar os dados.

#### **Criptografia e Autenticação**:

Para garantir a segurança das comunicações, será adotado um sistema híbrido que combina **AES** para criptografia eficiente de dados e **RSA** para criptografia de chaves, além de **assinaturas digitais** para autenticar a origem das mensagens.

1. **Criptografia com AES + RSA**: 
   - O Cliente criptografa a mensagem com uma chave **AES** gerada aleatoriamente.
   - A chave **AES** é então criptografada com a **chave pública** do **Cliente Master** (RSA), garantindo que somente o Cliente Master possa descriptografá-la com sua **chave privada**.
   - A mensagem, já criptografada com AES, e a chave AES criptografada são enviadas ao Cliente Master.

2. **Assinaturas Digitais**:
   - O Cliente também gera uma **assinatura digital** da mensagem criptografada usando sua **chave privada**.
   - A assinatura é anexada à mensagem antes de ser enviada ao Cliente Master.
   - O Cliente Master, ao receber a mensagem, usa a **chave pública** do Cliente para verificar a assinatura digital, garantindo que a mensagem não foi alterada e que foi realmente enviada pelo Cliente legítimo.

#### **Fluxo do Cliente**:
- **Passo 1**: O Cliente gera uma chave AES e criptografa a mensagem com ela.
- **Passo 2**: A chave AES é criptografada com a chave pública do Cliente Master (RSA).
- **Passo 3**: O Cliente gera uma assinatura digital da mensagem usando sua chave privada.
- **Passo 4**: O Cliente envia para o Cliente Master a mensagem criptografada, a chave AES criptografada e a assinatura digital.

### **Cliente Master**

O **Cliente Master** será responsável por enviar comandos criptografados aos clientes. Cada comando será associado ao **ID do destinatário**, e somente esse destinatário será capaz de descriptografá-lo. Os semeadores (clientes intermediários) repassarão o comando criptografado entre si até que o destinatário correto seja encontrado. Isso garante que os dados permaneçam protegidos durante todo o processo de transmissão.

#### **Criptografia e Assinatura dos Comandos pelo Cliente Master**:

Além disso, os comandos enviados pelo Cliente Master serão protegidos para garantir que não possam ser falsificados ou alterados durante a transmissão. Isso é feito por meio de **assinaturas digitais**.

1. **Assinatura Digital do Comando**: O Cliente Master gera o comando a ser enviado e **assina digitalmente o comando** com sua **chave privada**. Essa assinatura garante que somente o Cliente Master pode ter assinado aquele comando, e qualquer alteração subsequente na mensagem invalidará a assinatura.
   
2. **Verificação da Assinatura**: Quando um cliente recebe o comando, ele verifica a assinatura digital usando a **chave pública** do Cliente Master. Isso garante que o comando não foi alterado e que ele foi realmente emitido pelo Cliente Master.

3. **Criptografia do Comando** (Opcional): Caso o comando contenha informações sensíveis, ele pode ser criptografado com a **chave pública** do destinatário (cliente específico) ou com a **chave pública** do Cliente Master para garantir que ninguém, exceto o destinatário autorizado, possa ler ou alterar o conteúdo do comando.

#### **Fluxo do Cliente Master**:
- **Passo 1**: O Cliente Master gera um comando.
- **Passo 2**: O Cliente Master assina digitalmente o comando com sua chave privada.
- **Passo 3**: O Cliente Master envia o comando assinado e criptografado para os clientes.
- **Passo 4**: O Cliente verifica a assinatura digital do comando usando a chave pública do Cliente Master.
   - Se a assinatura for válida, o Cliente sabe que o comando é autêntico e não foi alterado.
   - Se a assinatura for inválida, o Cliente rejeita o comando.
- **Passo 5**: O Cliente pode então processar o comando conforme necessário.

### **Proteção Contra Falsificação e Interceptação**

- **Autenticidade**: A assinatura digital garante que o comando foi enviado pelo Cliente Master e que não foi falsificado.
- **Integridade**: A assinatura também garante que o comando não foi alterado durante o transporte. Se alguém modificar o comando após ele ser assinado, a verificação da assinatura falhará.
- **Confidencialidade**: Se o comando for criptografado, apenas o destinatário correto poderá lê-lo, garantindo que a informação não seja interceptada.
- **Segurança na Troca de Chaves**: A chave AES usada para criptografar as mensagens é protegida pelo algoritmo RSA, garantindo que apenas o Cliente Master tenha a chave privada necessária para descriptografá-la.
