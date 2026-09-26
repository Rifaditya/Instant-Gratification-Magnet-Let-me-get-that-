# 💻 Commandes Serveur & Prise en Charge Client Vanilla (MC 26.1.2)

> 📌 **Avertissement sur le Code Source du Dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement avant les versions publiques sur CurseForge et Modrinth.

| Encadré Technique | Paramètres Techniques |
| :--- | :--- |
| **Classe de Commande** | `net.instantgratification.magnet.MagnetCommand` |
| **Littéraux Principaux** | `/magnet` et `/ig_magnet` (Alias miroirs identiques) |
| **Sous-Commandes** | `toggle` |
| **Synchronisation Réseau** | `ServerPlayNetworking.send(player, new MagnetTogglePayload(newState))` |

---

## 📖 Référence des Commandes

### `/magnet toggle` (ou `/ig_magnet toggle`)
Bascule l'état personnel d'aimant du joueur exécutant entre activé et désactivé.

* **Syntaxe de la Commande** : `/magnet toggle`
* **Logique d'Exécution** :
  ```java
  private static int toggleMagnet(CommandSourceStack source) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      boolean newState = MagnetPlayerState.toggleMagnet(player);

      if (newState) {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.enabled"), false);
      } else {
          source.sendSuccess(() -> Component.translatable("chat.ig_magnet.disabled"), false);
      }

      if (ServerPlayNetworking.canSend(player, MagnetTogglePayload.TYPE)) {
          ServerPlayNetworking.send(player, new MagnetTogglePayload(newState));
      }
      return 1;
  }
  ```
* **Compatibilité Client Vanilla** : Permet aux joueurs se connectant depuis un client vanilla à un serveur Fabric de basculer leur aimant sans nécessiter l'installation du mod côté client.

---

## 🔗 Documentation Connexe
* [[Bascule Joueur & État de Session|fr_fr-26.1.2-Player-Toggle-and-Persistence]]
* [[Référence Complète des GameRules|fr_fr-26.1.2-GameRules]]
* [[Retour au Portail MC 26.1.2|fr_fr-26.1.2-Home]]
