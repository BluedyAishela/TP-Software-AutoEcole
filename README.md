
````

    ___   __  ____________  ________________  __    ______
   /   | / / / /_  __/ __ \/ ____/ ____/ __ \/ /   / ____/
  / /| |/ / / / / / / / / / __/ / /   / / / / /   / __/   
 / ___ / /_/ / / / / /_/ / /___/ /___/ /_/ / /___/ /___   
/_/  |_\____/ /_/  \____/_____/\____/\____/_____/_____/   
                                                          
````

# Application Software d'Auto-École

## Préambule

Ce projet a été réalisé lors de ma seconde année de [BTS SIO en option SLAM](https://www.onisep.fr/ressources/univers-formation/Formations/Post-bac/bts-services-informatiques-aux-organisations-option-b-solutions-logicielles-et-applications-metiers) suite 
à la demande des professeurs. 

Pour la réalisation de ce projet, nous étions en équipe de 2, [Neleoko](https://github.com/Neleoko) & [moi](https://github.com/BluedyRimuru).

## Installation

Vous devez récupérer le projet en effectuant la commande ci-dessous :
```shell
$ git clone https://github.com/BluedyAishela/Sunaria-BluedyNamic.git # HTTPS mais vous sélectionnez le lien que vous voulez.
```
Lorsque vous aurez récupéré le projet, vous devrez ajouter votre [jdk](https://www.oracle.com/fr/java/technologies/downloads/) au projet et importer les [librairies](https://github.com/BluedyAishela/TP-Software-AutoEcole/tree/main/Librairies) situées dans le projet.
Pour notre cas, nous avons utilisé l'application [IntelliJ](https://www.jetbrains.com/fr-fr/idea/) et le jdk est proposé par défaut.

Ceci étant fait, vous devrez ensuite exécuter cette commande et compléter le code en fonction de votre environnement.
```shell
$ cp .\src\Tools\ConnexionBDDExample.java .\src\Tools\ConnexionBDD.java
```

## Base de données 

Une fois que vous avez entré vos informations de base de données dans la fonction précédemment copiée,
vous devrez importer la base de données se situant dans le dossier nommé [bdd](https://github.com/BluedyAishela/TP-Software-AutoEcole/tree/main/bdd).

Nous proposons aussi l'installation via [Docker](https://www.docker.com/).

Création des containers via Docker :

```shell
$ docker-compose create
$ docker-compose start
```

## Identifiants utilisateurs

Les utilisateurs affiché ci-dessous sont des exemples d'utilisateurs

### Comptes élèves : 

- Ryu
- Salameche

### Compte moniteur :

- Bluedy

### Compte Responsable :

- Kagenou


Pour tous les utilisateurs, le mot de passe est : password.