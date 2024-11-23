

---

**Construção de um Sistema de Comunicação Anônima com Python, C, Java e Tor**

O objetivo deste sistema é criar uma plataforma de comunicação anônima e segura, permitindo que os clientes se comuniquem entre si e compartilhem informações de forma confidencial e sem riscos de interceptação. O sistema será composto por dois componentes principais: o **Cliente** e o **Cliente Master**.

### **Cliente**

Cada cliente do sistema terá uma porta aberta (porta 12137) para comunicação com outros clientes. Além disso, cada cliente terá um **ID único** de identificação. As comunicações entre os clientes serão criptografadas para garantir a confidencialidade das informações trocadas.

Para evitar a repetição de informações, cada dado transmitido terá uma **licença única**. Caso um cliente não possua a licença associada à informação, ele simplesmente rejeitará a mensagem. Assim, evita-se que informações sejam interceptadas ou reutilizadas de forma indevida. As mensagens serão criptografadas utilizando um esquema de criptografia exclusivo, onde somente o **operador** do sistema terá a chave para **descriptografar** a informação original. Os clientes, embora recebam as informações, não terão a capacidade de descriptografá-las, apenas de semear ou propagar os dados.

### **Cliente Master**

O **Cliente Master** será responsável por enviar comandos criptografados aos clientes. Cada comando será associado ao **ID do destinatário**, e somente esse destinatário será capaz de descriptografá-lo. Os semeadores (clientes intermediários) repassarão o comando criptografado entre si até que o destinatário correto seja encontrado. Isso garante que os dados permaneçam protegidos durante todo o processo de transmissão.
