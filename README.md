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

[GUI mock-ups.pdf](https://github.com/up201503995/LPOO1617_T5G11/files/967071/GUI.mock-ups.pdf)


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



