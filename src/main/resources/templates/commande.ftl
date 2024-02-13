<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmation de commande chez Laurich'App</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: #000;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .logo {
            margin-bottom: 20px;
            text-align: center;
        }
        .logo img {
            max-width: 250px;
            height: auto;
        }
        h2 {
            color: #fff;
        }
        p {
            color: #ccc;
            margin-bottom: 20px;
        }
        ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
            text-align: left;
        }
        li {
            margin-bottom: 10px;
            color: #fff;
        }
        a {
            color: #007bff;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="logo">
            <img src="https://cdn.discordapp.com/attachments/1194250354183905383/1205178654313807922/Fond_Noir_V2.png?ex=65d76d16&is=65c4f816&hm=b5b2ef967329c37ed5cd8751aff7e1714468f054cc8a33a5c8c5d8487482cba6&" alt="Logo de la Laurich'App">
        </div>
        <h2>Bonjour,</h2>
        <p>Merci pour votre commande chez Laurich'App. Nous espérons qu'elle vous plaira !</p>

        <p>Voici les détails de votre commande :</p>

<#--        <p><strong>N° de commande :</strong> ${commande.numero}</p>-->
<#--        <p><strong>Date de commande :</strong> ${commande.date}</p>-->
<#--        <p><strong>Montant total :</strong> ${commande.total} €</p>-->

        <p><strong>Articles commandés :</strong></p>
<#--        <ul>-->
<#--            <#list commande.produits as produit>-->
<#--                <li>${produit.nom} - Quantité: ${produit.quantite} - Prix unitaire: ${produit.prix} €</li>-->
<#--            </#list>-->
<#--        </ul>-->
        <p>Merci encore et à bientôt.</p>
        <p>Cordialement,</p>
        <p>Votre équipe Laurich'App.</p>
    </div>
</body>
</html>