###Sluck

Slack for SU

[Interface](https://picocli.info/)

Format des requêtes client par fonctionnalité:

>Connexion:
    ```/connectAs #pseudo #password```
> 
>*connecte l'utilisateur si il existe et que le mot de passe est correct*
> 
>*si l'utilisateur n'existe pas, le crée*
>
>*si le mot de passe est incorrect, retourne une erreur*

>Créer un channel:
```/create #channelName```
> 
> *si le channel n'existe pas, le crée avec l'utilisateur en propriétaire*
> 
> *si il existe, erreur*

>Se connecter à un channel:
```/join #channel```
> 
> *connecte l'utilisateur au channel si il existe, le crée sinon*

>Envoyer un message:
> ```/send #message```
> 
> *envoie un message de la part de l'utilisateur dans le channel où il est connecté*
> 
> */!\ il faudra également renvoyer le message à tous les utilisateurs connectés dans le channel en question*

>Se déconnecter d'un channel
> ```/disconnect```
> 
> *déconnecte l'utilisateur du channel courant*

>Supprimer un channel
> ```/delete #channelName```
> *supprime le channel si l'utilisateur n'y est pas connecté et en est le propriétaire*
