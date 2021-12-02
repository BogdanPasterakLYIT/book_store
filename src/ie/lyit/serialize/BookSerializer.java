/**
 * Class: B.Sc. in Computing
 * Instructor: Maria Boyle
 * Description: This class will contain the code that will enable an ArrayList of Book objects
 *              to be written to file, read from file, edited, deleted, viewed etc.
 * Date: dd/mm/yyyy
 * @author Maria Boyle
 * @version 1.0
**/
package ie.lyit.serialize;

import java.util.ArrayList;
import java.util.Scanner;
import ie.lyit.book.Book;
import java.io.*;

// This class will contain the code that will enable an ArrayList of Book objects
// to be written to file, read from file, edited, deleted, viewed etc.
public class BookSerializer {
	private ArrayList<Book> books;

	public final String FILENAME = "books.ser";

	// Default Constructor
	public BookSerializer() {
		// Construct bookList ArrayList
		books = new ArrayList<Book>();
	}

	/////////////////////////////////////////////////////////
	// Method Name : add() //
	// Return Type : void //
	// Parameters : None //
	// Purpose : Reads one Book record from the user //
	// and adds it to the ArrayList called books //
	/////////////////////////////////////////////////////////
	public void add() {
		// Create a Book object
		Book book = new Book();
		// Read its details
		book.read();
		// And add it to the books ArrayList
		books.add(book);
	}

	///////////////////////////////////////////////
	// Method Name : list() //
	// Return Type : void //
	// Parameters : None //
	// Purpose : Lists all Book records in books //
	///////////////////////////////////////////////
	public void list() {
		// for every Book object in books
		for (Book tmpBook : books)
			// display it
			System.out.println(tmpBook);
	}

	// This method will serialize the books ArrayList when called,
	// i.e. it will write it to a file called books.ser
	public void serializeBooks() {
		ObjectOutputStream os = null;
		try {
			// Serialize the ArrayList...
			FileOutputStream fileStream = new FileOutputStream(FILENAME);

			os = new ObjectOutputStream(fileStream);

			os.writeObject(books);
		} catch (FileNotFoundException fNFE) {
			System.out.println("Cannot create file " + FILENAME + ".");
		} catch (IOException ioE) {
			System.out.println(ioE.getMessage());
		} finally {
			try {
				os.close();
			} catch (IOException ioE) {
				System.out.println(ioE.getMessage());
			}
		}
	}

	// This method will deserialize the books ArrayList when called,
	// i.e. it will restore the ArrayList from the file books.ser
	public void deserializeBooks() {
		ObjectInputStream is = null;

		try {
			// Deserialize the ArrayList...
			FileInputStream fileStream = new FileInputStream(FILENAME);

			is = new ObjectInputStream(fileStream);

			books = (ArrayList<Book>) is.readObject();
			// set start number for new Book
			int max = 1000;
			for (Book book : books) {
				if (book.getLibraryNumber() >= max )
					max = book.getLibraryNumber() +1;
			}
			Book.setNextNumber(max);
		} catch (FileNotFoundException fNFE) {
			System.out.println("Cannot create file " + FILENAME + ".");
		} catch (IOException ioE) {
			System.out.println(ioE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException ioE) {
				System.out.println(ioE.getMessage());
			}
		}
	}

	public Book view() {
		Scanner keiboard = new Scanner(System.in);
		// Read the key field of the Book to be viewed from the user,
		System.out.println("Enter Library no. of book");
		int bookToView = keiboard.nextInt();
		// Loop around for every Book object in the ArrayList, and
		for(Book tmpBook : books) {
			// If its key field equals the key field of the Book to be viewed
			if (tmpBook.getLibraryNumber() == bookToView ) {
				// Display the object on screen.
				System.out.println(tmpBook);
				// And return it.
				return tmpBook;
			}
		}
		return null;
	}
	
	public void delete() {
		//Call the view() method to find, display, and return the Book
		Book bookToDelete = view();
		
		//If the Book to be deleted != null
		if (bookToDelete != null)
		
			//Delete it from the ArrayList 
			books.remove(bookToDelete);
	}
	
	public void edit () {
		// Call the view() method to find, display, and return the Book
		Book bookToEdit = view();
		
		//  If the Book to be edited != null
		if (bookToEdit != null) {
			int index = books.indexOf(bookToEdit);
			// Read in new details for it by calling it’s read()method,
			bookToEdit.read();
			// Reset it in the ArrayList to this new object using the ArrayList set()
			books.set(index, bookToEdit);
		}
	
	}
	
	
}