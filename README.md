# Space Invader - Android

## Summary

Bem vindo ao nosso projeto de Desenvolvimento de Jogos para Plataformas Móveis, um projeto  criado em Kotlin a partir do Android Studio com a implementação da FireBase.

## Space Invaders

O nosso projeto envolve o Space Invaders, um clássico no mundo dos jogos que oferece uma experiência única.

## Descrição

O objetivo no Space Invaders é eliminar os inimigos e tentar alcançar o maior número de ondas, usando uma nave controlada pelo jogador, evitando que estes cheguem à nave. Neste caso com a implementação de uma base de dados nomeada de "FireBase".
## Características

Um jogo com mecânicas simples, tornando-o jogável para qualquer pessoa.
Concebe uma experiência de competição pelo facto de haver uma scoreboard, onde naturalmente qualquer jogador vai querer ter o score mais alto dos listados.

## Mecânicas

Na parte inferior do ecrã, onde se encontra a nave do jogador, temos a zona de movimento. A nave irá mover-se consoante o lado que for pressionado, caso pressione no lado esquerdo a nave irá mover-se para esquerda e vice-versa.
Acima da zona de movimento temos a zona de disparo, que independentemente do lado que o user pressionar, a nave efetuará um disparo.

## Instalação

Abrir o Android Studio e importar o projeto diretamente deste repositório e depois é só aproveitar a experiência que o projeto tem a oferecer.

## Código
Seguidamente iremos mostrar blocos de código referentes a funções que achamos mais importantes:
Neste primeiro bloco podemos observar a função referente às mecânicas do jogo, tanto a nivel de movimentação como a nivel de disparo.
```kotlin
override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        val movArea = size.y - (size.y / 8)
        when (motionEvent.action and MotionEvent.ACTION_MASK) {

            MotionEvent.ACTION_DOWN-> {
                paused = false

                if (motionEvent.y > movArea) {
                    if (motionEvent.x > size.x / 2) {
                        playerShip.moving = PlayerShip.right
                    } else {
                        playerShip.moving = PlayerShip.left
                    }

                }

                if (motionEvent.y < movArea) {

                    playerBullet.shoot(
                        playerShip.position.left + playerShip.width / 2f,
                        playerShip.position.top,
                        playerBullet.up)
                }
            }

            MotionEvent.ACTION_UP -> {
                if (motionEvent.y > movArea) {
                    playerShip.moving = PlayerShip.stopped
                }
            }

        }
        return true
    }
```
O seguinte bloco de código é referente à Firebase, neste caso o código tem a funcionalidade de guardar e atualizar os scores dos utilizadores numa coleção presente na FireStore.
```kotlin
 val score = this.intent.getIntExtra("score", 0)

        val userEmail = auth.currentUser?.email

        if (userEmail != null) {
            val scoreData = hashMapOf(
                "score" to score
            )

            firestore
                .collection("scores")
                .document(userEmail)
                .set(scoreData)
                .addOnSuccessListener { documentReference ->
                    println("sucess")
                }
                .addOnFailureListener{e ->
                    println("fail ${e.message}")
                }
        }
```
O código abaixo serve para utilizar a coleção de scores anteriormente referida e transcrevê-la para uma lista nomeada de "scoreBoard", em que cada elemento desta lista guardará um email e o respetivo score. Esta lista poderá ser vizualizada na "ScoreBoardActivity". 
```kotlin
  firestore.collection("scores")
            .get()
            .addOnCompleteListener{
                for (scoreDoc in it.result) {
                    scoreBoard.add(Pair(scoreDoc.id, scoreDoc.getLong("score")!!.toInt()))
                }

                findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                listView.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
            }
```
## Imagens
Jogo:

![jogo](https://github.com/mendes001/MobileProj/assets/101103833/1248f348-7bc5-4641-91fe-6f5ac0176cb7)

Menu:

![main](https://github.com/mendes001/MobileProj/assets/101103833/74a6ab47-905f-4aff-ac3d-74adc4a6d361)

ScoreBoard:

![scoreboard](https://github.com/mendes001/MobileProj/assets/101103833/fa370aad-577b-4386-ac2b-1fcec005c5ba)

## Conclusão

Foi um bom desafio, pois podemos pôr em prática a matéria lecionada em aula. Achamos interessante a utilização da FireBase, que certamente irá ser útil para futuros projetos.

a23490 - Tiago Mendes
a23479 - Nuno Ribeiro







