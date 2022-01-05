Lancement de Sluck:

Lancer le projet sous Intellij: ouvrir dans une fenêtre le projet à la racine (côté Front), dans une autre le projet à la racine du serveur ./src (côté Back) où on ajoute au classpath le sql connector ( mysql:mysql-connector-java:8.0.11 )

Dans src/app, ajouter un fichier *config.cnf* contenant les lignes suivantes:

```
db_username=kxw4yyXXXXXX
db_url=jdbc:mysql://ZZZZZZ.eu-west-3.psdb.cloud/sluck?sslMode=VERIFY_IDENTITY
db_password=pscale_pw_FgvuMMBjEhEfxNhiWKRVpYYYYYY
```
**Remplacer les placeholders par les bons champs : 
XXXXXX : vi45mm
YYYYYY : Tc71BPFrGJFhKB-9Adqy2w
ZZZZZZ : gcvfvf8qih2d** 

Pour lancer le serveur:
Lancer l'exécution du main de la classe App dans ./src/app/ (IDE côté Back).
Vous disposez également dans ce package d'une classe Main qui permet de lancer un utilisateur uniquement en lignes de code.

Pour lancer les clients:
Dans l'IDE côté Front : configurer la classe MainFrame dans ./public/GUI de sorte à pouvoir lancer plusieurs instances simultanément (Allow Multiple Instances)
Lancer ensuite l'éxécution pour ouvrir la page de Sluck pour un client

Annexe: commandes client via Main
- Connexion:        signin #pseudo #password
- Créer un channel:     create_channel #channelName #userId
- Envoyer un message:   create_message #userName #channelId #message
- Se déconnecter:    signout
- Supprimer un channel:     delete_channel #channelName
