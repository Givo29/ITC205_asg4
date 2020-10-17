package library.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import library.entities.ILoan.LoanState;
import library.entities.helpers.BookHelper;
import library.entities.helpers.ILoanHelper;
import library.entities.helpers.LoanHelper;
import library.entities.helpers.PatronHelper;

class IncorrectCalculationOfFinesTest {
	
	static ILoanHelper loanHelper;
	static Book book;
	static Loan loan;
	static Patron patron;
	static ILibrary library;
	
	static Date now;
	static Date pastDueDate;

	@BeforeAll
	static void init() throws Exception {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(new Date());
		now = cal.getTime();
		cal.add(java.util.Calendar.HOUR, -24);
		pastDueDate = cal.getTime();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		library = new Library(new BookHelper(), new PatronHelper(), new LoanHelper());
		loanHelper = new LoanHelper();
		patron = new Patron("LN", "FN", "EM", 1L, 1);
		book = new Book("au1", "ti1", "cno1", 1);
		loan = new Loan(book, patron);
	}

	@Test
	void testReturnOverdueLoan() {
		loan.commit(1, pastDueDate);
		loan.state = LoanState.OVER_DUE;
		double amount = library.calculateOverDueFine(loan);

		assertTrue(amount > 0.0);
	}
	

}