package ProjectShop;

import java.util.Scanner;

class Shop {

	String customerName;
	String productName;
	double price;
	int quantity;
	int endingYear;
	int endingMonth;
	int endingDay;
	int countProductOfEachCustomer;
}

public class ProjectShop {

	static Shop[] shop = null;
	static Shop[] bell = null;
	static Shop[] customer = null;

	static int firstProductOfEachCustomer = 0;// لطباعة الفاتورة ابتداء من قيمة هذا المتغير عدا الزبون الاول من الصفر
	static int lastProductOfEachCustomer = 0;// لطباعة الفاتورة انتهاء عند قيمة هذا المتغير
	static int first = 0;
//firstproductOfE.. يتم اسناده للمتغير
	static int CountProduct;
	//عدد المنتجات التي يشتريها العميل
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {

		int choice;
		do {
			System.out.println("1. Add a product \n"
							   + "2. to see all products \n"
							   + "3. sale   and   print the bell\n"
							   + "4. to print closer date\n"
							   + "5. to see the earning \n"
							   + "----------\n"
							   + "0. exit");
			System.out.print("Enter choice : ");
			choice = in.nextInt();
			in.nextLine();
			switch (choice) {
			case 1:
				shop = reSize();
				shop = addProduct();
				//     System.out.println(shop.length);
				break;
			case 2:
				printproducts();
				break;
			case 3:
				bell = sale();
				printBell(bell);
				break;
			case 4:
				closerDate();  // or we can use Temp to see the closer date
				break;
			case 5:
				earning();
				break;
			case 0:
				System.out.println("Goodـbey");
				break;
			default:
				System.out.println("\033[33mSorry, out of choice\033[0m ");
				break;
			}
			if (choice != 0) {
				// لإعادة طباعة القائمة (لرؤية المخرج بوضوح )
				System.out.print("Enter any key to get back (Enter) : ");
				in.nextLine();
			}
		} while (choice != 0);
	}

	static Shop[] reSize() {
		return reSize(shop);
	}

	static Shop[] reSize(Shop[] menu) {
		int length;
		if (menu == null) {
			length = 1;
		} else {
			length = menu.length + 1;
		}
		Shop[] newSize = new Shop[length];
		for (int i = 0; i < length - 1; i++) {
			newSize[i] = menu[i];
		}
		return newSize;
	}

	static Shop[] addProduct() {
		Shop s = new Shop();

		System.out.print("Enter name of product : ");
		s.productName = in.nextLine();
		System.out.print("Enter the price $ : ");
		s.price = in.nextDouble();
		System.out.print("Enter quantity of the product you have : ");
		s.quantity = in.nextInt();
		System.out.print("Enter ending year : ");
		s.endingYear = in.nextInt();
		System.out.print("Enter ending month : ");
		s.endingMonth = in.nextInt();
		System.out.print("Enter ending day : ");
		s.endingDay = in.nextInt();
		in.nextLine();
		shop[shop.length - 1] = s;
		return shop;
	}

	static Shop[] sale() {
		if (shop == null) {
			System.out.println("\033[31mYou have to add product first !! \033[0m");
		} else {
			Shop[] newBell = null;
			System.out.print("Enter the customer name : ");
			customer = reSize(customer);
			customer[customer.length - 1] = new Shop();
			customer[customer.length - 1].customerName = in.nextLine();
			firstProductOfEachCustomer = first;

			CountProduct = 0;
			while (true) {

				newBell = reSize(newBell);
				System.out.print("Enter product " + (CountProduct + 1) + " : ");
				newBell[CountProduct] = new Shop();

				String name = in.nextLine();
				int index;
				if ((index = search(name)) != -1) {
					int count;
					newBell[CountProduct].productName = name;

					while (true) {
						System.out.print("Enter count of " + newBell[CountProduct].productName + " : ");
						newBell[CountProduct].quantity = count = in.nextInt();
						in.nextLine();

						if (shop[index].quantity >= count) {
							shop[index].quantity -= count;
							newBell[CountProduct].price = count * shop[index].price;
							// لجساب الإجمالي لكل زبون بمفرده
							customer[customer.length - 1].price += newBell[CountProduct].price;
							break;
						}
						System.out.println("we just have " + shop[index].quantity);
					}

					bell = reSize(bell);
					bell[bell.length - 1] = newBell[CountProduct];
					CountProduct++;// زيادة عدد المنتجات
					lastProductOfEachCustomer++;
				} else {
					System.out.println("this product is not exisit .");
				}
				System.out.println("press \"1\" to continue \npress \"Any number \" to stop");
				if (in.nextByte() != 1) {

					break;
				}
				in.nextLine();
			}
			in.nextLine();
		}
		first = lastProductOfEachCustomer;
		return bell;

	}

	static void printBell(Shop[] bell) {
		if (bell != null) {
			System.out.println("\033[35m____________________________________\033[0m");

			System.out.println("customer : " + customer[customer.length - 1].customerName);


			System.out.println("name   :  unit  :  price ");

			int sum = 0;
			int count = 0;
			for (int i = firstProductOfEachCustomer; i < lastProductOfEachCustomer; i++) {
				System.out.println((count + 1) + "|" + " " + bell[i].productName + "  :  " + bell[i].quantity + "  : " + bell[i].price + " $ ");
				sum += bell[i].price;
				count++;
			}
			System.out.println("total $ : " + sum);
			System.out.println("\033[35m____________________________________\033[0m");
		}
	}

	static int search(String name) {
		for (int i = 0; i < shop.length; i++) {
			if (shop[i].productName.equalsIgnoreCase(name)) {
				return i;
			}
		}
		return -1;
	}

	static void closerDate() {
		if (shop == null) {
			System.out.println("\033[31mYou have not entered any product yet !! \033[0m");
		} else {
			System.out.print("Enter current year : ");
			int currentYear = in.nextInt();
			System.out.print("Enter current month : ");
			int currentMonth = in.nextInt();
			System.out.print("Enter current day : ");
			int currentDay = in.nextInt();
			System.out.println();
			for (int i = 0; i < shop.length; i++) {
				int months = shop[i].endingMonth - currentMonth;

				if (currentYear == shop[i].endingYear) {
					if (months <= 4 & months >= 0) {
						int days = shop[i].endingDay - currentDay;
						if (months == 0 & (days <= 30 & days >= 1)) {
							System.out.println(shop[i].productName + " | has " + days + " days");
						} else {
							System.out.println(shop[i].productName + " | has " + months + " months");
						}
					}
				} else if ((currentYear + 1) == shop[i].endingYear) {
					months = (12 - currentMonth + shop[i].endingMonth);
					if (months <= 4 & months >= 1) {
						System.out.println(shop[i].productName + " | has " + months + " months");
					}
				}
			}
			in.nextLine();
			System.out.println();
		}
	}

	public static void earning() {

		//لا يكرر اسماء الزبائن في الفاتورة  & ينقص الكمية من جميع المشتريين
		double earnSum = 0.0;
		if (customer == null) {
			System.out.println("\033[31mYou have not sold any thing !! \033[0m");
		} else {
			for (int i = 0; i < bell.length; i++) {
				earnSum += bell[i].price;
			}
			System.out.println("\033[33m-------------------------\033[32m");
			System.out.println("The earning : " + earnSum + "  $$$");
			System.out.println("\033[33m-------------------------\033[0m");
			System.out.print("Enter \"1\" to see all the earning of each customer \n\"any number to stop\" : ");
			if (in.nextByte() == 1) {
				System.out.println("\033[36m");
				for (int i = 0; i < customer.length; i++) {
					System.out.println("\"" + customer[i].customerName + "\" " + customer[i].price + " $");

				}
			}
			System.out.println("\033[0m");
			in.nextLine();
		}
	}

	public static void printproducts() {
		if (shop == null) {
			System.out.println("\033[31mThere is no product .\033[0m");
		} else {
			System.out.printf("\033[%sm_______________________\033[0m \n", 33);
			System.out.println(" name\tcount\texpiry_date\tprice");
			for (int i = 0; i < shop.length; i++) {
				System.out.println((i + 1) + "| "
								   + shop[i].productName
								   + "\t" + shop[i].quantity
								   + "\t" + shop[i].endingYear + "\\" + shop[i].endingMonth + "\\" + shop[i].endingDay
								   + "\t  " + shop[i].price + " $");
			}
			System.out.println("\033[33m_______________________\033[0m ");
		}
	}
}