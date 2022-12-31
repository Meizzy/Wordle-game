package burujiyaseer.example.wordle

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import burujiyaseer.example.wordle.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(),
                        NoticeDialogFragment.NoticeDialogListener{
    private var currentRow = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val btn = findViewById<Button>(R.id.check)

//        binding.check.isEnabled = false
        Log.d(TAG,"onCreate: starts")

        moveFocus()
        binding.check.setOnClickListener {
            gameLoop()
        }
        binding.refresh.setOnClickListener {
            clearInputs()
        }

    }
    private fun getUserInputWord(currentRow: LinearLayout): String {
        val correctWord = Words().getTodayWord().lowercase()
        Log.d(TAG, "*******SHAME!!!!****** noob")
        Log.d(TAG, "Answer is $correctWord noob")
        Log.d(TAG, "*******SHAME!!!!****** noob")
        binding.check.isEnabled = true
        var userWord = ""

        val inputRow = currentRow
        for (i in 0 until inputRow.childCount) {
            val editText = inputRow.getChildAt(i) as EditText
            val currentLetter = editText.text.toString().lowercase()

            if (currentLetter.isNotBlank()) {

                if (correctWord[i].toString() == currentLetter) {
                    editText.background =
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.background_right,
                            null
                        )

                } else if (correctWord.contains(currentLetter)) {
                    editText.background =
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.background_wrong_position,
                            null
                        )

                } else {
                    editText.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.background_wrong, null)
                }
                editText.isEnabled = false
                userWord += currentLetter
            }
        }
        return userWord
    }
    /**
     *Function to check if word provided by the user is in the word list. If word is not in the word list user doesn't loose his attempt and can retype the word.
     * @param //wordList word list with all words available in the game.
     * @return boolean value if userWord is in the word list. True if so, else false.
     */


    private fun gameLoop() {
        val correctWord = Words().getTodayWord().lowercase()
        val newDialog = NoticeDialogFragment()


            if (this.currentRow < binding.inputLayout.childCount) {
                val nowRow = binding.inputLayout.getChildAt(this.currentRow) as LinearLayout
                val userInputWord = getUserInputWord(nowRow)
                if (userInputWord == correctWord) {

                        Toast.makeText(this, "Congratulations", Toast.LENGTH_LONG).show()
                    newDialog.show(supportFragmentManager, "game")
                } else {
                    Toast.makeText(this, "Not this time", Toast.LENGTH_LONG).show()
                }
                this.currentRow += 1
            }
        else{
                    Toast.makeText(this, "GameOver!", Toast.LENGTH_LONG).show()
                newDialog.show(supportFragmentManager, "game")
        }
    }

    private fun clearInputs() {
        Log.d(TAG, "Clearing inputs")
        this.currentRow = 0
        for (i in 0 until binding.inputLayout.childCount) {
            val inputRow = binding.inputLayout.getChildAt(i) as LinearLayout
            for (j in 0 until inputRow.childCount) {
                val input = inputRow.getChildAt(j) as EditText
                input.setText("")
                input.background =
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.background,
                        null
                    )
                input.isEnabled = true
//                input.imeOptions = EditorInfo.IME_ACTION_DONE
            }
        }
        binding.inputLayout.getChildAt(0).requestFocus()
    }



    override fun onDialogPositiveClick(dialog: DialogFragment) {
        clearInputs()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        finishAffinity()
    }

    private fun moveFocus() {
        Log.d(TAG, "MoveFocus")
        this.currentRow = 0
        var nextIterator: Int
        for (i in 0 until binding.inputLayout.childCount) {
            val inputRow = binding.inputLayout.getChildAt(i) as LinearLayout
            for (j in 0 until inputRow.childCount-1) {
                val input = inputRow.getChildAt(j) as EditText
                nextIterator = j + 1
                val nextInput = inputRow.getChildAt(nextIterator) as EditText
                input.doAfterTextChanged { nextInput.requestFocus() }

            }
        }
    }
}
