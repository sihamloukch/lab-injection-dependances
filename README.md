# Projet : Injection de dépendances en Java

## 1. Introduction

Ce projet a pour objectif de comprendre et de mettre en pratique le principe de l'injection de dépendances en Java, en utilisant plusieurs méthodes : l'instanciation statique, l'instanciation dynamique et le framework Spring.

L'objectif principal est de réduire le couplage entre les composants d'une application afin de faciliter sa maintenance, son évolution et sa réutilisation.

## 2. Objectifs du TP

- Comprendre le principe du couplage faible.
- Créer des interfaces et leurs implémentations.
- Comprendre le fonctionnement de l'injection de dépendances.
- Réaliser l'injection par instanciation statique.
- Réaliser l'injection par instanciation dynamique.
- Utiliser le framework Spring pour gérer les dépendances.

## 3. Partie 1 : Création des interfaces et des implémentations

### 3.1 Création de l'interface IDao

L'interface `IDao` contient une méthode `getData()` qui permet de récupérer des données.

```java
public interface IDao {
    double getData();
}
```

### 3.2 Création de l'implémentation de IDao

La classe `DaoImpl` implémente l'interface `IDao` et fournit une définition concrète de la méthode `getData()`.

```java
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        return 100;
    }
}
```

### 3.3 Création de l'interface IMetier

L'interface `IMetier` contient une méthode `calcul()` qui permet d'effectuer un traitement métier.

```java
public interface IMetier {
    double calcul();
}
```

### 3.4 Création de l'implémentation de IMetier

La classe `MetierImpl` implémente l'interface `IMetier`. Elle utilise l'interface `IDao` pour récupérer les données.

```java
public class MetierImpl implements IMetier {

    private IDao dao;

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        double data = dao.getData();
        return data * 2;
    }
}
```

Cette approche permet d'utiliser une interface au lieu de dépendre directement d'une classe concrète. Cela représente le principe du couplage faible.

## 4. Partie 2 : Injection des dépendances

### 4.1 Injection par instanciation statique

Dans cette méthode, les objets sont créés directement dans le programme principal.

```java
public class Presentation {
    public static void main(String[] args) {

        IDao dao = new DaoImpl();

        MetierImpl metier = new MetierImpl();
        metier.setDao(dao);

        System.out.println("Résultat : " + metier.calcul());
    }
}
```

**Explication :** La dépendance est créée manuellement à l'aide de l'opérateur `new`, puis injectée dans la classe métier.

### 4.2 Injection par instanciation dynamique

Cette méthode utilise la réflexion Java (Reflection) pour créer les objets à partir de leurs noms de classes.

Exemple :

```java
import java.io.File;
import java.util.Scanner;

public class Presentation2 {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(new File("config.txt"));

        String daoClassName = scanner.nextLine();
        String metierClassName = scanner.nextLine();

        Class cDao = Class.forName(daoClassName);
        IDao dao = (IDao) cDao.getDeclaredConstructor().newInstance();

        Class cMetier = Class.forName(metierClassName);
        IMetier metier = (IMetier) cMetier.getDeclaredConstructor().newInstance();

        ((MetierImpl) metier).setDao(dao);

        System.out.println("Résultat : " + metier.calcul());
    }
}
```

Le fichier `config.txt` contient :

```text
ma.projet.DaoImpl
ma.projet.MetierImpl
```

**Explication :** Les classes sont chargées dynamiquement à partir du fichier de configuration, ce qui permet de changer les implémentations sans modifier le programme principal.

*Remarque : Adapter les noms des packages aux classes réelles du projet.*

### 4.3 Injection des dépendances avec Spring XML

Spring permet de gérer automatiquement les dépendances entre les objets à l'aide d'un fichier XML.

Exemple de configuration `config.xml` :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dao" class="ma.projet.DaoImpl"/>

    <bean id="metier" class="ma.projet.MetierImpl">
        <property name="dao" ref="dao"/>
    </bean>

</beans>
```

Classe principale :

```java
ApplicationContext context =
    new ClassPathXmlApplicationContext("config.xml");

IMetier metier = (IMetier) context.getBean("metier");

System.out.println("Résultat : " + metier.calcul());
```

**Explication :** Spring crée les objets et injecte automatiquement la dépendance `dao` dans `metier` à partir du fichier XML.

### 4.4 Injection des dépendances avec Spring Annotations

Spring permet également de gérer les dépendances avec des annotations, sans utiliser un fichier XML.

**Classe DaoImpl :**

```java
@Repository("dao")
public class DaoImpl implements IDao {

    @Override
    public double getData() {
        return 100;
    }
}
```

**Classe MetierImpl :**

```java
@Service("metier")
public class MetierImpl implements IMetier {

    @Autowired
    private IDao dao;

    @Override
    public double calcul() {
        return dao.getData() * 2;
    }
}
```

**Classe principale :**

```java
ApplicationContext context =
    new AnnotationConfigApplicationContext("ma.projet");

IMetier metier = (IMetier) context.getBean("metier");

System.out.println("Résultat : " + metier.calcul());
```

**Explication :** Les annotations `@Repository`, `@Service` et `@Autowired` permettent à Spring de détecter les composants et d'injecter automatiquement les dépendances.

## 5. Comparaison des méthodes d'injection

| Méthode | Principe |
|---|---|
| Instanciation statique | Création manuelle avec `new` |
| Instanciation dynamique | Création avec Reflection |
| Spring XML | Injection avec un fichier XML |
| Spring Annotations | Injection avec des annotations |

## 6. Conclusion

Ce TP m'a permis de comprendre le principe de l'injection de dépendances et son importance dans le développement d'applications Java.

J'ai découvert différentes méthodes d'injection et compris que le framework Spring facilite la gestion des dépendances et permet de développer des applications avec un couplage faible, plus flexibles et plus faciles à maintenir.

## 7. Repository GitHub

Lien du projet : https://github.com/sihamloukch/lab-injection-dependances.git
