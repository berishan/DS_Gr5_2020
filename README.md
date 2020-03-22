## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Instructions](#instructions)


## General info
This project contains simple encryption tecniques.



## Technologies
 This project is created with:
* Java JDK 13


## Instructions
##### Beale Cipher

* use   java ds beale encrypt < book > <plaintext>    or   java ds beale decrypt < book > < ciphertext >
*  With this method, each letter in the secret message is replaced with a number which represents the position of a word in the book which starts with this letter.   
*Example:
       In this example we've used a file that contains the following text " the quick brown fox jumps over the lazy dog."
       Plaintext: pershendetje nga fiek
       Ciphertext: 24 3 12 25 2 3 15 41 3 1 21 3 4 15 43 37 4 17 7 3 9



                 
##### Caesar Cipher
* use    java ds caesar encrypt < key >  <plaintext>   or    java ds caesar decrypt < key > < ciphertext >   or   java ds caesar bruteForce < plaintext >
* Each letter is shifted along in the alphabet by the same number of letters.
* Example:
        In this example, each letter in the plaintext message has been shifted 4 letters down in the alphabet.
        Plaintext: pershendetje nga fiek
        Ciphertext: tivwlirhixni rke jmio
        
        
        
        
        
##### Playfair Cipher
* use   java  ds playfair encrypt < key > <plaintext>  or    java  ds playfair decrypt < key > < ciphertext >       
add --table if you want the table to be shown
* The Playfair cipher uses a 5 by 5 table containing a key word or phrase. To generate the table, one would first fill in the spaces of the table with the letters of the keyword (dropping any duplicate letters), then fill the remaining spaces with the rest of the letters of the alphabet in order (to reduce the alphabet to fit you can either omit "Q" or replace "J" with "I")
  To encrypt a message, one would break the message into groups of 2 letters.
     1.If both letters are the same, add an X between them. Encrypt the new pair, re-pair the remining letters and continue.
     2.If the letters appear on the same row of your table, replace them with the letters to their immediate right respectively, wrapping around to the left side of the row if necessary. For example, using the table above, the letter pair GJ would be encoded as HF.
     3.If the letters appear on the same column of your table, replace them with the letters immediately below, wrapping around to the top if necessary. For example, using the table above, the letter pair MD would be encoded as UG.
     4.If the letters are on different rows and columns, replace them with the letters on the same row respectively but at the other pair of corners of the rectangle defined by the original pair. The order is important - the first letter of the pair should be replaced first. For example, using the table above, the letter pair EB would be encoded as WD. 
*Example:
        In this example we build the table with the keyword "topi".
        Plaintext: pershendetje nga fiek
        Ciphertext:id su lc rb bi el vn fm el rp
