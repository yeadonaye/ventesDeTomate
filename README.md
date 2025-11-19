# 🍅 S201 Tomates — Java Swing Shop

Une application Java Swing amusante pour parcourir de délicieuses variétés de tomates, les ajouter à un panier et persister les changements de stock dans un fichier JSON. Construite avec amour pour les tomates et pour un code propre.

## 🌟 Points forts

* Catalogue riche et vues détaillées des variétés
* Panier avec édition des quantités et totaux
* Gestion du stock : limitation automatique des quantités et actions désactivées en cas de rupture
* Persistance JSON : mises à jour en temps réel sauvegardées dans `src/main/resources/data/tomatesSauvegarde.json`
* Chargement intelligent : préfère le fichier de sauvegarde s'il est présent sinon `tomates.json`

## 🗂 Table des matières

* Présentation
* Points forts
* Démarrage rapide
* Flux applicatif
* Persistance du stock
* Classes principales
* Notes & recommandations
* Vérification du comportement
* Fun facts tomates
* Contribution
* Structure du projet

## 🚀 Démarrage rapide

* Prérequis : `Java 1.8` + IDE recommandé (IntelliJ/Eclipse/VS Code)
* Exécution depuis l'IDE :

  * Ouvrir le projet
  * Lancer `ihm.PageGarde#main`
* Build avec Maven (optionnel) :

  ```bash
  mvn -q -DskipTests=true compile
  ```

  *Astuce : si Maven n'est pas installé, exécuter depuis l'IDE est le plus simple*

## 🧭 Flux applicatif

1. Ouvrir le catalogue et choisir une tomate
2. Depuis la page détail, définir la quantité et cliquer sur « Ajouter au panier »
3. Le stock se décrémente immédiatement et se sauvegarde dans `tomatesSauvegarde.json`
4. Dans le panier, modifier les quantités → synchronisation du stock avec sauvegarde instantanée

## 🧺 Persistance du stock

* Fichier de sauvegarde : `src/main/resources/data/tomatesSauvegarde.json`
* Données par défaut : `src/main/resources/data/tomates.json` (utilisé si aucune sauvegarde)
* Utilitaire : `modèle.OutilsBaseDonneesTomates#mettreAJourStockTomateDansJson(String chemin, Tomate tomate)`
* Logique :

  * Si `tomatesSauvegarde.json` n'existe pas → initialisation depuis `tomates.json`
  * Mise à jour du champ `stock` à chaque action dans le panier

Exemple JSON :

```json
[
  {
    "désignation": "Tomate cœur de bœuf",
    "stock": 12,
    "prix": 2.50,
    "couleur": "rouge"
  }
]
```

## 🧩 Classes principales

* `ihm.PageGarde` : fenêtre principale
* `ihm.ListeTomates` : liste du catalogue (chargement JSON)
* `ihm.DetailUnTomate` : vue détail + gestion du panier
* `ihm.Panier` : UI du panier avec quantités éditables
* `service.PanierService` : logique du panier + persistance
* `service.StockService` : vérification des stocks
* `modèle.OutilsBaseDonneesTomates` : gestion JSON

## ⚠️ Notes & recommandations

* Écriture dans les ressources = OK en dev ; en production, utiliser un chemin externe (ex : `%APPDATA%/S201_TOMATES/`)
* Pour réinitialiser le stock → supprimer `tomatesSauvegarde.json`

## 🧪 Vérification du comportement

* Ajout au panier → impossible de dépasser le stock disponible
* Réduction dans le panier → restitution au stock
* Vérifier `tomatesSauvegarde.json` après chaque action
* Redémarrage → le stock doit être conservé

## 🌱 Fun facts tomates

* Fruit botanique, mais légume en cuisine !
* Variétés multicolores, comme dans notre UI 🍅

## 🤝 Contribution

* Forker → modifier → Pull Request

Idées d'amélioration :

* Chemin de persistance configurable
* Plugin `maven-exec` pour simplifier l'exécution
* Tests unitaires

## 📜 Structure du projet

```
S201_TOMATES/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/   # code source
│   │   └── resources/
│   │       └── data/
│   │           ├── tomates.json
│   │           └── tomatesSauvegarde.json
│   └── test/
```

Merci et bon appétit… de code ! 🍅👨‍💻
