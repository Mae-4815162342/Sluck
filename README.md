###Sluck

Slack for SU

[Interface](https://picocli.info/)

Format des requêtes client par fonctionnalité:

>Connexion:
    ```signin #pseudo #password```
> 
>*connecte l'utilisateur si il existe et que le mot de passe est correct*
> 
>*si l'utilisateur n'existe pas, le crée*
>
>*si le mot de passe est incorrect, retourne une erreur*

>Créer un channel:
```create_channel #channelName #userId```
> 
> *si le channel n'existe pas, le crée avec l'utilisateur en propriétaire*
> 
> *si il existe, erreur*

>Envoyer un message:
> ```create_message #userName #channelId #message```
> 
> *envoie un message de la part de l'utilisateur dans le channel où il est connecté*
> 
> */!\ il faudra également renvoyer le message à tous les utilisateurs connectés dans le channel en question*

>Se déconnecter d'un channel
> ```signout```
> 
> *déconnecte l'utilisateur du channel courant*

>Supprimer un channel
> ```delete_channel #channelName```
> *supprime le channel si l'utilisateur n'y est pas connecté et en est le propriétaire*
