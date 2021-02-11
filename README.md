![Diagramme de classe](https://github.com/clementor5/AppliPompes/blob/main/img/logo_application.png)

# AppliPompes

Petite application android qui permet la gestion simplifiée du challenge sportif des pompes journalières.

Le principe est simple, chaque jour de l'année, vous devrez effectuez le nombre de pompes correspondant au numéro de ce jour dans l'année.

Par exemple le premier Janvier vous ferez 1 pompe, puis le 2 Janvier vous ferez 2 pompes ... et ainsi de suite jsuqu'au 31 Decembre où vous devrez faire 365 pompes.

Il est possible de configurer la difficultée du challenge pour le rendre plus difficile.

L'application permet de definir une heure à laquelle l'utilisateur souhaite que l'application fasse apparaitre une notification pour lui rappeler de faire ses pompes journalieres. Cette notification peut être activée ou desactivée.

## Vous pouvez installer l'application en téléchargeant ce fichier :
[Application](https://github.com/clementor5/AppliPompes/raw/main/apk/app-debug.apk)

## Il vous suffit ensuite de l'exécuter SOUS ANDROID et de suivre le processus d'installation.

### L'application est divisée en 3 parties :
- Une page d'accueil
- Une page de configuration
- Une page de succes

#### Page d'accueil
C'est la page qui apparait pas defaut au lancement de l'application, mais vous pouvez vous rendre sur cette page en cliquant sur l'icone de maison.

Cette page indique le nombre de pompes que vous avez fait depuis le début de l'année, et indique si vous avez des pompes d'avance ou de retard.

Elle permet de saisir un nombre de pompes que vous avez fait en plus.

ELle indique aussi combien de pompes vous devrez faire au total dans l'année.

Le bouton en bas de la page explique rapidement en quoi consiste le challenge sportif.

![Diagramme de classe](https://github.com/clementor5/AppliPompes/blob/main/img/accueil_1.png)
![Diagramme de classe](https://github.com/clementor5/AppliPompes/blob/main/img/accueil_2.png)

#### Page de configuration
Vous pouvez vous rendre sur cette page en cliquant sur l'icone de configuration (un engrenage).

Cette page permet de choisir la difficultée du challenge sportif, il existe 4 difficultées : Normal, Difficile, Extreme et Impossible.

En fonction de la difficultée choisie, vous pouvez observer le nombre de pompes à faire au total dans l'année.

Cette page permet la saisie de l'heure à laquelle la notification de rappel apparaitra chaque jour, cette notification peut être desactivée.

Le dernier bouton de la page réinitialise complètement l'application, celle-ci retrouvera le même état qu'après son installation.

![Diagramme de classe](https://github.com/clementor5/AppliPompes/blob/main/img/configuration_1.png)
![Diagramme de classe](https://github.com/clementor5/AppliPompes/blob/main/img/configuration_2.png)

#### Page de succes
Vous pouvez vous rendre sur cette page en cliquant sur l'icone de coupe.

Cette page liste les succes de l'application.

Les succes sont atteints en faisant un certain nombre de pompes dans l'année.

Lorsqu'un succes est atteint, il apparait en rouge, s'il n'est pas atteint, il apparait en gris.

![Diagramme de classe](https://github.com/clementor5/AppliPompes/blob/main/img/succes.png)
