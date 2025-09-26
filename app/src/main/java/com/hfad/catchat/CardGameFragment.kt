package com.hfad.catchat

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment

class CardGameFragment : Fragment() {

    private lateinit var cards: Array<ImageButton>
    private var cardImages = mutableListOf(
        R.drawable.five, R.drawable.five,
        R.drawable.three, R.drawable.three,
        R.drawable.joker, R.drawable.joker,
        R.drawable.jack, R.drawable.jack,
        R.drawable.jack
    )
    private var selectedCard: ImageButton? = null
    private var matchedPairs = 0
    private var attempts = 0
    private lateinit var attemptsText: TextView
    private var handler = Handler()
    private var cardBack = R.drawable.card_back

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_card_game, container, false)
        setupGame(view)
        view.findViewById<Button>(R.id.restart_button).setOnClickListener {
            restartGame(view)
        }
        return view
    }

    private fun setupGame(view: View) {
        cardImages.shuffle()
        val cardGrid = view.findViewById<GridLayout>(R.id.card_grid)
        attemptsText = view.findViewById(R.id.attempts_text)
        cards = Array(9) { i ->
            val card = cardGrid.getChildAt(i) as ImageButton
            card.setImageResource(cardBack)
            card.tag = cardImages[i]
            card.setOnClickListener { onCardClicked(card) }
            card
        }
    }

    private fun onCardClicked(card: ImageButton) {
        if (card.drawable.constantState == resources.getDrawable(cardBack, null).constantState) {
            card.setImageResource(card.tag as Int)
        } else {
            return // Card is already flipped
        }

        if (selectedCard == null) {
            selectedCard = card
            handler.postDelayed({
                if (selectedCard != null) {
                    selectedCard?.setImageResource(cardBack)
                    selectedCard = null
                }
            }, 3000)
        } else {
            if (selectedCard!!.tag == card.tag) {
                // Match found
                selectedCard?.setOnClickListener(null)
                card.setOnClickListener(null)
                selectedCard = null
                matchedPairs++
                if (matchedPairs == cardImages.size / 2) {
                    if (attempts < 5) {
                        attemptsText.text = "遊戲結束 /•᷅‎‎•᷄\\୭ 你嘗試了 $attempts 次 優秀！"
                    } else {
                        attemptsText.text = "遊戲結束 /•᷅‎‎•᷄\\୭ 你嘗試了 $attempts 次 再接再厲～"
                    }
                }
            } else {
                // No match
                attempts++
                attemptsText.text = "嘗試次數: $attempts"
                handler.postDelayed({
                    selectedCard?.setImageResource(cardBack)
                    card.setImageResource(cardBack)
                    selectedCard = null
                }, 1000)
            }
        }
    }

    private fun restartGame(view: View) {
        cardImages.shuffle()
        cards.forEachIndexed { index, card ->
            card.setImageResource(cardBack)
            card.tag = cardImages[index]
            card.setOnClickListener { onCardClicked(card) }
        }
        selectedCard = null
        matchedPairs = 0
        attempts = 0
        attemptsText.text = "嘗試次數: 0"
    }
}
