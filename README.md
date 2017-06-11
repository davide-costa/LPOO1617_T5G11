Final Project Delivery


Setup/Installation procedure

BattleShip:
Para correr o projeto (modo development) com o eclipse basta fazer clone do branch do repositório e correr a função main da classe GuiMain.

Para correr o jogo (modo release) usando o jar é necessário ter na mesma pasta do jar, uma pasta res com a pasta imagens dentro (contém as imagens necessárias ao jogo. É necessário ter na mesma pasta do jar também o executavel chromedriver.exe.
O google chrome deve estar instalado no computador que corre o jogo e deve ter a pasta de instalaçao na localização default.


BattleShipServer:
Para correr o projeto (modo development) com o eclipse basta fazer clone do branch do repositório e correr a função main da classe BattleShipServer.

Para correr o servidor (modo release) usando o jar é necessário ter na mesma pasta do jar o ficheiro bshipPlayers.bship que contém a informacão das contas a criadas no servidor. Este ficheiro deve existir sempre, mesmo quando ainda nao existem contas.



Design Patterns

	Singleton
A classe Client, da package sockets, no client-side, utiliza o deisgn pattern Singleton pois apenas faz sentido existir uma instância da classe que implementa a lógica de comunicação de sockets. Por outro lado, torna-se vantajoso poder aceder a essa classe de qualquer parte do código pois é necessária em várias classes que agem como intermediários entre outras partes do código e a comuniucação nos sockets. Desta forma nao é necessário a essas outras partes guardar uma instância da classe Client, não necessitando de a conhecer, aumentando assim o encapsulamento. Apenas é conhecida pelos intermediários que podem vários obter a sua instância.


	Strategy
No modo singleplayer, a classe AIOpponent é reponsável por computar jogadas seguindo duas estratégias. Inicialmente as jogadas são aleatórias, no entanto, quando é atingido um barco, a estratégia muda e a inteligência artificial vai produzir uma nova jogada atacando uma das celulas adjacentes. (esta funcionalidade nao foi, no entanto, totalmente implementada).


	Observer
Cada célula do mapa de jogo (classe GameMap) tem um estado (classe CellState). Pode estar revelada (ao adversário) ou oculta, isto é indicado por um boleano existente na classe CellState. A GUI observa o GameMap, que por sua vez observa cada uma das suas celulas (classe CellState). O GameMap é notificado quando uma celula é alterada e por sua vez notifica a GUI. Desta forma, a GUI é atualizada quando houver uma alteraçao à informação da célula (mudança de estado da classe CellState).


	Flyweight
As imagens dos barcos (classe Ship) e da água (representa uma célula que não contém nenhum barco) utilizadas no jogo, quando repetidas causam um custo excessivo de desempenho e uso de memória. Dessa forma, imagens mesmo que usadas varias vezes, apenas são carregadas para memória uma vez.
Todas as imagens do jogo são desta forma carregadas no inicio do programa para a classe ImagesData. Esta classe contém como atributos, publicos, as imagens do BattleShip devidamente carregadas, que depois podem ser acedidas de qualquer sitio no programa.



Other relevant decisions
//TODO

Major difficulties along the way
A principal e inicial dificuldade neste trabalho foi, como seria esperado, fazer a parte do Multiplayer do jogo. Esta parte está bastante elaborada pois conta com uma aplicação java dedicada unicamente a esta parte: Um Servidor. Utilizámos um padrão de implementação de arquitetura Server/Client. No entanto, foi necessário perceber cuidadosamente cada uma das linhas de código do padrão para o adptar às nossas necessidades. O código é bastante complexo, dado que utiliza também multithreading, criando uma nova thread para cada client que se liga para ficar à escuta por dados enviados por esse client.
Também foi necessário algum esforço na parte do client side para fazer o "coupling" entre as várias classes q implementam a lógica de comunicação de modo a obter um código "maintainable".
No entanto a interface Seraializable, facilitou a troca de informação entre o client e o servidor.
Como o grupo investiu bastante tempo nesta parte, não houve tempo para completar o desenvolvimento do SinglePlayer. No entanto, foi iniciado e o código está preparado e devidamente "coupeled" para receber essa nova funcionalidade.

Lessons learned
Aprendemos que a interface Serializable do java e as Object Streams são bastante úteis para tudo o que seja transmitir informação: desde gravar dados em ficheiros até enviar dados pela internet. Utilizando estas funcionalidade da linguagem java, conseguímos implementar a comunicação entre o cliente e o servidor de forma simples e legível.

Overall time spent developing
Cada membro do grupo gastou, aproximadamente, 125 horas a desenvolver este projeto.

Work distribution amongst team members
Consideramos que a distribuição de trabalho por ambos os membros do grupo foi uniforme. Tendo, assim, cada membro do grupo realizado uma carga de trabalho de 50%.



User manual


	Main Menu
![main menu](https://user-images.githubusercontent.com/25772485/27013714-56bd2b66-4ee1-11e7-8405-44e3eb5949e9.png)

Este é o Menu principal. O jogador pode escollher o modo de jogo: SinglePlayer ou MultiPlayer.
O modo SinglePlayer não está concluído, portanto o botão não se encontra ativo.
O modo Multiplayer leva o jogador para um ecrã de login, no qual se deve autenticar com as suas credencias, no nosso servidor.

	Login Screen
![login](https://user-images.githubusercontent.com/25772485/27013754-343ce972-4ee2-11e7-8a7f-97e6768f3c68.png)

Este é a screen de login. O jogador deve introduzir as suas credenciais de login para se autenticar no servidor.
Se o Username ainda não existir, é criada uma nova conta.
Se este já existir, a password é verificada e, se não corresponder à password correta (validada pelo servidor), é mostrada uma mensagem a informar tal.
Se for criada uma nova conta ou a password estiver correta, o login é bem sucedido e o utilizador é levado para o Lobby, descrito a seguir.
Caso seja criada uma conta, é apresentada a mensagem na imagem seguinte:

![new account created](https://user-images.githubusercontent.com/25772485/27014021-b14a76c8-4ee7-11e7-858d-a12dec9a1a53.png)

	Lobby Screen
![lobby with no players](https://user-images.githubusercontent.com/25772485/27013787-b0f84fc4-4ee2-11e7-889f-4e84e63dcfdb.png)

Esta é a screen de Lobby. Aqui aparecem todos os jogadores que estão atualmente no Lobby, no servidor. A lista de jogadores no lobby é fornecida pela servidor. Neste caso, não existem jogadores online, portanto a lista está vazia.

A seguinte imagem mostra o Lobby com 1 jogador online:
![lobby with a player online](https://user-images.githubusercontent.com/25772485/27013786-b0f7f560-4ee2-11e7-8e82-c7adb7916363.png)


Para convidar um jogador para um jogo, deve clicar-se como botão esquerdo do rato em cima do jogador que se pretende convidar.
Na aplicação do jogador do outro lado (que recebe o convite) irá aprecer um ConfirmDialog a informar que foi convidade. Tendo a opção para aceitar e rejeitar o convite.
Caso seja rejeitado, o outro jogador é informado de tal.
Caso seja aceite, ambos os jogadores são colocados em Ship Placement.
A screen de Ship Placement apresenta-se a seguir:

	Ship Placement
![shipplacement](https://user-images.githubusercontent.com/25772485/27013835-7c20ac50-4ee3-11e7-98b3-ecce8e81ad8a.png)

Esta é a screen de Ship Placement. Aqui os jogadores podem colocar os barcos no mapa. Os barcos aparecem na área ao lado direito, como se apresenta na imagem acima.
Para colocar um barco no mapa, o joagador deve clicar com o botão esquerdo do rato em cima do barco em que quer pegar. Para pousar o barco, deve arrastá-lo até ao sítio pretendido (arrastando o rato até esse mesmo sítio) e largar o botão esquerdo para pousar. Tal ação é devidamente validada, sendo impossível colocar barcos fora do alcance do mapa, em cima de outros ou nas redondezas (surrounding coords) de outros barcos. Se o jogador pretender rodar o barco, enquanto está com ele "pegado", ou seja, depois de pegar nele, deve clicar com o botão direito para rodar o barco. Depois, para pousar o barco, deve proceder da forma anteriormente indicada: largar o botão esquerdo do rato.
Está disponível a funcionalidade de colocar os barcos automaticamente no mapa, clicando no botão "Place Ships Automatically".
Quando estiver pronto para jogar, deve clicar no botão "Ready". Isto irá informar o servidor que se encarrega de informar o adversário.
Quando o adversário estiver pronto, é apresentado um label que diz "Opponent Ready", como se apresenta na imagem seguinte:

![shipplacement with opponent ready](https://user-images.githubusercontent.com/25772485/27013836-7c25075a-4ee3-11e7-9b77-539d7237ceaf.png)

Quando ambos os jogadores estiverem prontos, são colocados no jogo. A aplicação de ambos irá mudar para a screen de Game, como se apresenta a seguir.

![beggining of a game](https://user-images.githubusercontent.com/25772485/27013933-5542150e-4ee5-11e7-9bbc-c0a8dff5c2e8.png)

Esta é a screen de Game, ou seja, que ambos os jogadores vêm. No exemplo é mostrado o estado de um jogo que acaba de começar com o mapa do aliado sem qualquer tiro assim como do adversário. O deste último encontra-se também oculto e vai sendo revelado pelo outro jogador (através do servidor) à medida que os disparos vão acontecendo.
Para disparar no mapa do adversário, o utilizador deve clicar com o botão esquerdo do rato em cima da célula sobre a qual pretende disparar.
Os barcos que são atingidos vão sendo marcados com X. As células com água que vão sendo atingidas (e, portanto, reveladas) são marcadas com uma textura de água a ferver.
Os barcos quando sao destruídos têm as suas imagens alteradas quando são completamente destruídos, ficando uma textura de explosão.


Quando o jogo acaba um jogador é derrotado (Defeat) e outro é vitorioso (Victory).
Ambos os jogadores são devidamente informados em cada uma destas situações. A seguir são apresentadas as imagens que caracterizam cada um destes casos:

![victory screen](https://user-images.githubusercontent.com/25772485/27014045-5dcaf54e-4ee8-11e7-827a-d46457befb73.png)
Esta é a screen de vitoria.

Apresenta-se no canto inferior direito um botão para o jogador poder partilhar a sua vitória no Facebook.
É aberta uma janela do navegador Chrome para efetuar o login e, depois, a partilha no Facebook da sua vitória.
Depois de partilhar a sua vitória no Facebook, quando voltar ao jogo, ser-lhe-á apresentada a seguinte imagem:

![victory screen after posting on facebook](https://user-images.githubusercontent.com/25772485/27014044-5dc8e24a-4ee8-11e7-924d-840096165a4d.png)

No caso de derrota, é apresentada a seguinte screen ao jogador derrotado:
![defeat screen](https://user-images.githubusercontent.com/25772485/27014080-44d1ec40-4ee9-11e7-9b13-e909aa1255f7.png)



Esta screen é apresentada quando o jogador clica no botão "Exit" do Menu Principal. Permite confirmar se realmente pretende sair do programa ou não.
![exit screen](https://user-images.githubusercontent.com/25772485/27014085-6f07d57e-4ee9-11e7-91cc-d6a7908872dc.png)

Final Project Intermediate Check-Point


   UML Diagrams
   
   Client Side
![battleship uml model](https://cloud.githubusercontent.com/assets/25772498/25568524/938dd816-2dfc-11e7-9bd5-3dd3ea340c3e.png)


![bship logic](https://cloud.githubusercontent.com/assets/25772498/25568527/93916288-2dfc-11e7-8e81-c6bde8f756fc.png)


![bship network data](https://cloud.githubusercontent.com/assets/25772498/25568528/939584f8-2dfc-11e7-9675-12005cf22be1.png)


![bship network socket](https://cloud.githubusercontent.com/assets/25772498/25568526/938f7fd6-2dfc-11e7-98e1-7845d57caefb.png)


![bship ai](https://cloud.githubusercontent.com/assets/25772498/25568525/938e10ce-2dfc-11e7-8470-d50a7f73e6d9.png)





Server Side


![battleshipserver uml model](https://cloud.githubusercontent.com/assets/25772498/25568538/c93b480e-2dfc-11e7-94e4-d2715d454840.png)


![bship logic](https://cloud.githubusercontent.com/assets/25772498/25568537/c937b176-2dfc-11e7-834e-fa442ce9d52c.png)


![bship network data](https://cloud.githubusercontent.com/assets/25772498/25568536/c9372b52-2dfc-11e7-940d-c99c895cfd00.png)


![bship network socket](https://cloud.githubusercontent.com/assets/25772498/25568539/c93da5e0-2dfc-11e7-8090-d93e5f2cb51c.png)

      
      
      
      

        Describing classes' responsability:


                                                     Client side
                                                    
       bship.logic                                                         
	Game
	A classe game é responsável por implementar toda lógica de jogo. Guarda informação sobre o estado atual do jogo (mapas (do próprio jogador e adversário) e adversário (uma classe do tipo Opponent que representa o outro jogador). O atributo que representa o adversário é responsável por estabelecer a comunicação de e para esse.
	
	Opponent
	A classe Opponent é responsável por representar o jogador adversário e fazer a interface de interação com o mesmo. Este adversário pode ser inteligência artificial (em modo singleplayer) ou outro jogador (ser humano) remoto (em modo multiplayer). Esta classe tem subclasses para singleplayer (SinglePlayerOpponent) e multiplayer (MultiPlayerOpponent).
	No caso de ser singleplayer, informa a classe de inteligência artificial (AiOpponent da package ai) e é informada por esta das jogadas. No caso de multiplayer informa a classe de comunicação (Client, da package sockets) e é informada por esta das jogadas.

	GameMap
	Esta classe representa um mapa do jogo. Contém uma matriz (array bidimensional) para representar o mapa. Cada elemento da matriz é uma classe do tipo CellState, que representa o estado atual de uma célula e informações sobre ela. Permite acrescentar mapas ao jogo, criando subclasses desta (expandable code). Por defeito só existe um tipo de mapa (subclasse) com o nome DefaultMap.
	
	CellState
	Esta classe representa o estado atual de uma célula e o barco que contém (se aplicável, pois uma célula pode não conter um barco). Um dos atributos da classe é um apontador para o barco que lá estiver (caso haja algum).
	
	Coords
	Esta classe representa as coordenadas 2D (x e y) de uma célula no jogo. Tem métodos que permitem fazer várias operações necessárias com coordenadas, isolando, ao máximo, as responsabilidades de cada objeto.
	
	Ship
	Esta classe representa um barco. É utilizada pela Game para facilitar identificação do destruição (isolar responsabilidades). Também é utilizada pela GUI para desenhar no ecrã os gráficos associados a cada barco, utilizando informações internas ao barco diferentes das utilizadas pela classe Game.
	Tem várias subclasses que representam os diferentes tipos de barcos. Desta forma, será bastante fácil acrescentar novos tipos de barcos ao jogo. As atuais subclasses são: Carrier, BattleShip, Submarine, Cruiser e Destroyer.

        bship.ai
	AiOpponent
	Esta classe implementa a lógica de inteligência artificial para jogar em modo singleplayer.
	
        bship.network.data
	BattleShipData
	Esta classe interface representa qualquer tipo de informação que se pretende trocar entre o cliente e o servidor para satisfazer as necessidades da nossa aplicação do modo multiplayer. Os vários tipos de informação (consoante o estado da aplicação) estão representados como subclasses desta.
	Utilizaremos sockets para trocar a informação. A informação trocada através dos sockets são sempre objetos do tipo BattleShipData.
	Para comunicar, o cliente e o servidor, criam uma classe apropriadam, que é do tipo BattleShipData (mas intanciada como subclasses dela), e enviam-na através do socket.
	
	A subclasse LoginData representa informação trocada quando um jogador efetua login. Esta, por sua vez, tem subclasses que representam o pedido (do cliente) e a resposta (do servidor).
	
	A subclasse LobbyData representa a informação trocada quando um jogador está no salão de espera. Após um jogador fazer o login, é colocado num salão de espera (lobby), à espera que o convidem ou enquanto espera para convidar outro jogador). Esta, por sua vez, tem subclasses que representam a lógica de convites:
	 - LobbyInviteData. Convite feito a outro jogador, enviado do cliente para o servidor.
	 - LobbyInvitedData. Convite feito ao próprio jogador, enviado do servidor para o cliente. Este convite foi feito por outro jogador e reencaminhado pelo servidor para o jogador convidado.
	 - LobbyInvitedResponseData. Resposta do jogador ao convite feito por outro jogador, enviado do cliente (convidado) para o servidor.
	 - LobbyInviteResponseData. A resposta a um convite feito pelo jogador, enviado do servidor para o cliente. Esta resposta veio de outro jogador e é reencaminhada pelo servidor para o jogador que fez o convite.
	
	A subclasse ShipPlacementData representa a informação trocada enquanto os jogadores estão no estado de posicionamento dos barcos no mapa. Esta, por sua vez, tem subclasses que representam a lógica de sincronizar o início do jogo para o instante em que ambos os jogadores estão prontos:
	 - ReadyForGameData. Esta classe serve para o jogador informar ao servidor que está pronto para jogar e que já colocou os seus barcos no mapa. É enviada uma instância desta classe quando o jogador acaba de colocar os seus barcos no mapa e está pronto para começar o jogo. É enviada do cliente para o servidor.
	 - StartGameData. Esta classe serve para o servidor indicar a ambos os jogadores, que estão no estado de posicionamento dos barcos no mapa, que o jogo vai começar. O servidor encarrega-se de avisar ambos os jogadores apenas depois de se certificar que estão ambos prontos para começar a jogar, ou seja, depois de ter recebido de ambos os jogadores dados do tipo ReadyForGameData. É enviada do servidor para o cliente.

	A subclasse GameData representa a informação trocada durante um jogo de batalha naval. Esta, por sua vez, tem subclasses que representam as ações feitas no jogo:
	 - GameShootData. Esta classe contém a informação sobre uma jogada que consiste num disparo. Inicialmente, o jogador dispara no campo do advsersário e uma instância desta classe é enviada do cliente para o servidor, contendo as coordenadas de disparo. Em seguida, o servidor envia a mesma instância para o adversário do jogador que disparou.
	 - GameResultData. Esta classe contém a informação sobre o efeito de uma jogada que consiste num disparo. Depois do jogador receber a informação do disparo do adversário (classe GameShoot), calcula os efeitos desse disparo na sua frota e dá conhecimento ao advsersário desses mesmos efeitos (o que estava na célula (barco ou água) e, caso a jogada tenha provocado o afundamento de um barco, qual foi esse barco). Dá também conhecimento ao advsersário se a jogada provocou a sua derrota (fim do jogo), através de uma flag desta classe. Uma instância contendo estas informações é enviada do cliente (jogador que foi atacado) para o servidor e, em seguida, o servidor reecaminha a instância para o cliente do jogador que atacou.
	 - EndOfGameData. Esta classe é enviada aquando do fim de um jogo. (A identificação de fim de jogo é feita através da classe GameResult). Quando uma jogada provoca o fim do jogo, o jogador atacado informa o seu adversário da sua derrota, através da referida classe. O jogador que perdeu, deve ficar a conhecer o campo do adversário ainda oculto (posições dos barcos que faltavam destruir). Portanto, o jogador que ganhou, ao receber a indicação de fim de jogo pela classe GameResult, deve enviar uma instância da classe EndOfGame que contém a informação do seu mapa, para que o adversário tome conhecimento do mesmo.

	 
        bship.network.sockets
	Client
	Esta classe contém o código necessário para trocar informação usando sockets e implementa toda a lógica dessa mesma comunicação do lado do cliente. A comunicação é feita usando objetos do tipo BattleShipData (instanciados como suas derivadas). Para tal, são criadas streams de objetos a partir das streams nativas da classe Socket (input e output). Assim, são enviados objetos do tipo BattleShipData através do socket que liga o cliente ao servidor.



                                                 Server side

        bship.network.sockets
	Server
	Esta classe contém o código necessário para trocar informação usando sockets e implementa toda a lógica de receção e envio de informação através de sockets do lado do servidor. A classe chama o método accept da classe ServerSocket, ficando presa à espera de novas ligações. Quando recebe uma ligação, cria uma nova instância da classe ClientThread para satisfazer esse cliente. Em seguida, cria uma nova thread, usando o construtor que recebe um objeto do tipo Runnable cujo método run irá ser chamado.
	
	ClientThread
	Esta classe é reponsável por comunicar com cada cliente. É subclasse de Runnable. É sempre criada uma nova thread para cada cliente e, portanto, também um nova instância desta classe. Essa thread chama o método run desta classe que contém uma chamada ao método readObject, ficando a thread presa à espera de informação na stream do socket (à espera que um client envie informação ao servidor). Quando recebe informação (que é do tipo BattleShipData), informa a classe Player da informação e esta é responsável por a processar e fazer as devidas ações. Quando é necessário enviar informação a um cliente, a classe Player chama o método desta classe (ClientThread) responsável por enviar a informação para a outputStream do socket.
	
        bship.network.data
	Esta package tem exatamente o mesmo conteúdo que a do client, uma vez que se trata de classes que representam a informação que é trocada entre o servidor e o cliente e, portanto, ambos têm que conhecer a "linguagem".
	
        bship.logic
	BattleShipServer
	Esta classe contém a informação necessária ao funcionamento do servidor para satisfazer todos os clientes. Contém um ArrayList de jogadores (classe Player) com todos os jogadores que estão online no momento. E outros métodos necessários para o funcionamento do servidor.

	Player
	Esta classe representa um jogador de BattleShip que está atualmente online e ligado ao servidor. Guarda informação sobre o seu estado atual, o seu nickname e uma referência para a classe responsável por comunicar com esses jogador (classe do tipo ClientThread, da package bship.network.sockets).
	Como já foi referido, quando é recebida informação de um jogador, a classe ClientThread (que a recebe), informa a classe Player dessa mesma informação (classe do tipo BattleShipData). Esta informação pode ser entendida como um evento. Esta classe funciona também como uma máquina de estados: sabe o estado atual do jogador (classe PlayerState) e recebe um evento. A classe PlayerState implementa as ações necessárias a tomar para esse evento, com base no estado atual, sendo que esse estado é representado na forma de subclasses de PlayerState.
	
	PlayerState
	Esta classe interface representa o estado de um jogador que faz parte de uma máquina de estados. Tem um método responsável por analisar um evento (e efetuar as devidas ações), ou seja, uma classe de dados (do tipo BattleShipData) recebida do jogador. Esta, por sua vez, tem subclasses que representam os diferentes estados de um jogador no BattleShip e que redefinem o método responsável por analisar o evento e efetuar as devidas ações. Esse método é diferente consoante o estado em que o jogador está, tal como numa máquina de estados.
	 - InLobby. Representa o estado de um jogador quando está no Lobby, ou seja, à espera de ser convidado por outros jogadores para jogar. Redefine o método que analiza um evento para o estado específico InLobby e toma as devidas ações.
	 - InShipPlacement. Representa o estado de um jogador quando está no posicionamento de barcos. Contém um atributo que indentifica se está pronto para jogar. Redefine o método que analiza um evento para o estado específico InShipPlacement e toma as devidas ações.
	 - InGame. Representa o estado de um jogador quando está num jogo de BattleShip. Redefine o método que analiza um evento para o estado específico InGame e toma as devidas ações.
         
         
       
   Sequence Diagrams


![authentication sequence](https://cloud.githubusercontent.com/assets/25772498/25568026/026873dc-2df2-11e7-851b-c42a4405e0bd.png)


![game singleplayer client-side](https://cloud.githubusercontent.com/assets/25772498/25568029/0ee75042-2df2-11e7-8f98-eec47a35d137.png)


![lobby](https://cloud.githubusercontent.com/assets/25772498/25568024/025f572a-2df2-11e7-8575-8b4eef91896a.png)


![shipplacement](https://cloud.githubusercontent.com/assets/25772498/25568025/02673bc0-2df2-11e7-9d61-418ec96eddc3.png)



![game multiplayer client server simplified](https://cloud.githubusercontent.com/assets/25772498/25568621/98e1df5e-2dfe-11e7-81fb-51c129b6c7a6.png)



![game multiplayer client-side](https://cloud.githubusercontent.com/assets/25772498/25568049/aaf8d618-2df2-11e7-905b-d1414ba1f0e1.png)




![game multiplayer server-side](https://cloud.githubusercontent.com/assets/25772498/25568031/0eeba4da-2df2-11e7-923f-6e51ec7c62c9.png)





Design Patterns

	Singleton
A classe Game, responsável por implementar toda lógica de jogo, é acedida em diferentes pontos do código, no entanto, deve apenas existir uma instância dessa classe que é usada em todos esses pontos do código.
O uso do design pattern Singleton permite-nos alcançar facilmente este objetivo.

	Template
O jogo apresenta dois modos de jogo, singleplayer e multiplayer. No entanto, a sequência de passos a seguir para o bom funcionamento de cada uma dos modos é semelhante. Apenas difere na forma como os jogadores informam uma ao outro as jogadas e os efeitos delas, feitas pelas classes derivadas de Opponent. No caso do singleplayer é chamada a classe SingleplayerOpponent e no caso do multiplayer a classe MultiplayerOpponent.

	Strategy
No modo singleplayer, a classe AIOpponent é reponsável por computar jogadas seguindo duas estratégias. Inicialmente as jogadas são aleatórias, no entanto, quando é atingido um barco, a estratégia muda e a inteligência artificial vai produzir uma nova jogada atacando uma das celulas adjacentes.

	Observer
Cada célula do mapa de jogo (classe GameMap) tem um estado (classe CellState). Pode estar revelada (ao adversário) ou oculta, isto é indicado por um boleano existente na classe CellState. Utilizando um Observer a GUI é atualizada quando houver uma alteraçao á informação da célula (mudança de estado da classe CellState).

	Flyweight
As imagens dos barcos (classe Ship) e da água (representa uma célula que não contém nenhum barco) utilizadas no jogo, quando repetidas causam um custo excessivo de desempenho e uso de memória. Dessa forma, imagens mesmo que usadas varias vezes, apenas são carregadas para memória uma vez.




GUI Design

	Gui Functionalities
	
	-SinglePlayer
	-MultiPlayer
 	-Facebook login
 	-Game server login
 	-Disparar numa celula
 	-Posicionar os barcos no mapa
 	-Partilhar uma vitória na utilizando o Facebook
	-Fechar o jogo



GUI mock-ups

[GUI mock-ups.pdf](https://github.com/up201503995/LPOO1617_T5G11/files/967072/GUI.mock-ups.pdf)


Unit Tests

						Client side

	bship.logic
	Game
	Testar fazer um disparo no campo do advsersário.
	 - Jogador faz um disparo numa célula já revelada (barco ou água). (Não deve ser possível fazê-lo. O teste assegura que não é possível.)
	 - Jogador faz um disparo numa célula não revelada. (Verificar que a classe responsável por notificar o outro jogador o efetua corretamente.)
	
	Fazer um disparo no próprio campo. (Função que é chamada quando o adversário efetua um disparo e verificar se os efeitos foram os esperados.)
		 - Disparar numa célula que seja água. Verificar que ficou marcada como revelada no mapa do próprio jogador. Verificar que o outro jogador foi informado de como está o local onde disparou (o local deve ser revelado).
		 - Disparar numa célula que tenha um barco. Verificar que ficou marcada como revelada no mapa do próprio jogador. Verificar que o outro jogador foi informado de como está o local onde disparou (o local deve ser revelado).
		 - Disparar numa célula que tenha um barco e destruir esse barco. Verificar que ficou marcada como revelada no mapa do próprio jogador. Verificar que o outro jogador foi informado de como está o local onde disparou (o local deve ser revelado). Verificar que o jogador tomou também conhecimento de qual o barco que foi destruído e que as células à (1 célula de distância) volta foram todas reveladas.
		
						Server side
	bship.logic
	Testar o login de um jogador no servidor. Simular um pedido de um cliente (uma nova ligação ao socket do servidor) e garantir que o jogador foi adicionado ao ArrayList de jogadores.
	
	Testar a sequência de um convite do lado do servidor. (Simular a receção de um convite de um jogador)
	 - Jogador online convidado recebe o convite do jogador que o convidou. (Simular vários jogadores online para ter a certeza que vai para o jogador certo).
	 - Jogador convidado aceita o convite e jogador que convidou recebe essa resposta.
	 - Jogador convidado recusa convite e jogador que convidou recebe essa resposta.
	
	Testar o início de um jogo.
	 - Jogo começa apenas quando dois jogadores estão prontos para jogar (testar se isso acontece apenas nesse caso).
	
	Testar a sequência de uma jogada do lado do servidor.
	 - Servidor recebe um pedido de disparo de um jogador e informa o outro desse pedido.
	 - Servidor recebe um resultado de uma jogada e informa o outro jogador desse resultado.



