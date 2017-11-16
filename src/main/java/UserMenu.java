import java.util.Scanner;

/**
 * @author Christian on 07.11.2017.
 */
public class UserMenu {

	private Movie movie = new Movie();
	private boolean flag = true;
	private Scanner clientInput = new Scanner(System.in);
	private String choice;

	public void startMenu(){
		while (flag){

			System.out.println(
					"** Skriv inn ønsket valg og avslutt med enter **\n\n" +
							"------------------------------------------------------------------------------------------------- \n" +
							"1: Koble til og opprett databasen\n" +
							"0: Avslutt\n" +
							"-------------------------------------------------------------------------------------------------");


			choice = clientInput.nextLine();

			switch (choice) {
				case "1":

					movie.readConfigFile();

					//Creates a connection to movieDB and creating DAO
					movie.establishConnection(movie.getUsername(), movie.getPassword());

					//creating table
					movie.createTable();

					movie.fillTableWithInitData();

					printElementsMenu();
					break;

				case "0":
					System.out.print("Avslutter...");
					flag = false;
					System.out.println("Vellykket");
					break;

				default:
					System.out.println("Feil svar! Prøv igjen... \n");

					//rekursivt kall hvis ikke klienten svarer som forventet
					startMenu();
					break;


			}
		}
	}

	public void printElementsMenu() {
		while (flag) {

			System.out.println(
							"------------------------------------------------------------------------------------------------- \n" +
							"1: Print ut data fra tabellen\n" +
									"2: Get row by id\n" +
									"3: Get rows by year\n" +
									"4: Add a row to the table\n" +
									"5: Delete row by id\n" +
							"0: Avslutt\n" +
							"-------------------------------------------------------------------------------------------------");


			choice = clientInput.nextLine();

			switch (choice) {
				case "1":
					movie.printAll();
					printElementsMenu();
					break;

				case "2":
					System.out.print("Enter movie-id: ");
					int id = Integer.parseInt(clientInput.nextLine());
					movie.getRowById(id);
					printElementsMenu();
					break;

				case "3":
					System.out.print("Enter year: ");
					int year = Integer.parseInt(clientInput.nextLine());
					movie.getRowsByYear(year);
					printElementsMenu();
					break;

				case "4":
					System.out.println("Choose a title:");
					String createTitle = clientInput.nextLine();

					System.out.println("Choose a director:");
					String createDirector = clientInput.nextLine();

					System.out.println("Choose a year:");
					int createYear = clientInput.nextInt();

					movie.addRow(createTitle,createDirector,createYear);

					break;
				case "5":
					int deleteId = Integer.parseInt(clientInput.nextLine());
					movie.deleteRow(deleteId);
					printElementsMenu();
					break;

				case "0":
					System.out.print("Avslutter...");
					flag = false;
					System.out.println("Vellykket");
					break;

				default:
					System.out.println("Feil svar... Prøv igjen");
					printElementsMenu();
					break;
			}
		}
	}

}
