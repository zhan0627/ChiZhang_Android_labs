package algonquin.cst2335.chizhangandroidlabs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * this is main java class for login and button
 * @author chi zhang
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {
    /**
     * this holds the text at the center of the screen
     */
    TextView tv = null;
    /**
     * this holds the editText as the password
     */
    EditText et = null;
    /**
     * this holds the button as login
     */
    Button btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText et = findViewById(R.id.pw);
        TextView tv = findViewById(R.id.textView);
        Button btn = findViewById(R.id.loginButton);

        btn.setOnClickListener(clk -> {
            String password = et.getText().toString();
            if (checkPasswordComplexity(password)) {

                tv.setText("Your password meets the requirements");

            } else {

                tv.setText("You shall not pass!");
            }

        });
    }
/**
 * this function is check thre password
 * @param pw The String object that we are checking
 * method checkPasswordComplexity
 * @return return true if there are uppercase letter, lowercase letter, number, and special character inside the password.
 * */

    public boolean checkPasswordComplexity(String pw) {

        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

            for (int i = 0; i < pw.length(); i++) {
                if (!foundUpperCase) {
                    if (Character.isUpperCase(pw.charAt(i))) {
                        System.out.println(pw.charAt(i));
                        foundUpperCase = true;
                        //    assertTrue(foundUpperCase);
                    }
                }

                if (!foundLowerCase) {
                    if (Character.isLowerCase(pw.charAt(i))) {
                        System.out.println(pw.charAt(i));
                        foundLowerCase = true;

                    }
                }

                if (!foundNumber) {
                    if (Character.isDigit(pw.charAt(i))) {
                        //System.out.println(pw.charAt(i));
                        foundNumber = true;

                    }
                }
                /** This function checks if ps have #$%^&*!@? in the string
                 *
                 * @param pw The String object that we are checking
                 * @return return true if pw have #$%^&*!@?
                 */
                if (!foundSpecial) {
                    switch (pw.charAt(i)) {
                        case '#':
                        case '?':
                        case '*':
                        case '$':
                        case '%':
                        case '^':
                        case '&':
                        case '!':
                        case '@':
                            foundSpecial = true;
                        break;
                        default:
                            foundSpecial = false;
                    }
                }

            }

            if (!foundUpperCase) {

                Toast.makeText(getApplicationContext(), "miss uppercase letter ", Toast.LENGTH_SHORT).show();
                /** check the uppercase letter */
                return false;

            } else if (!foundLowerCase) {
                Toast.makeText(getApplicationContext(), "miss lowercase letter ", Toast.LENGTH_SHORT).show();   // Say that they are missing a lower case letter;
                /** check the lowercase letter */
                return false;

            } else if (!foundNumber) {

                Toast.makeText(getApplicationContext(), "miss number ", Toast.LENGTH_SHORT).show();
                /** check the number letter */
                return false;
            } else if (!foundSpecial) {
                Toast.makeText(getApplicationContext(), "miss special character", Toast.LENGTH_SHORT).show();
                /** check the special letter  */
                return false;

            } else {
                //Toast.makeText(getApplicationContext(), "Your password meets the requirements", Toast.LENGTH_SHORT).show();
                return true; /**only get here if they're all true*/
            }

        }

    //}
}