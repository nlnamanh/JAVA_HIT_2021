package View;

import Controller.*;
import Model.Account;
import Model.Bill;
import Model.Customer;
import Model.Pet;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class RunMain {

    static Scanner sc = new Scanner(System.in);
    static List<Account> accountList = new ArrayList<>();
    static List<Pet> petList = new ArrayList<>();
    static List<Customer> customerList = new ArrayList<>();
    static List<Bill> billList = new ArrayList<>();
    static FileController fileController = new FileController();

    public static void main(String[] args) {
        userAccuracy();
    }

    private static void userAccuracy() {
        int choice;
        while (true) {
            System.out.println("------------ Chức năng ------------");
            System.out.println("1. Dành cho admin ");
            System.out.println("2. Dành cho khách hàng ");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    showMenuAdmin();
                    break;
                case 2:

                    break;
                case 0:
                    System.out.println("Thoát thành công!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Chọn sai chức năng!");
                    userAccuracy();
            }
        }
    }

    private static void showMenuAdmin() {
        int choice;
        while (true) {
            System.out.println("------------ Tài Khoản ------------");
            System.out.println("1. Tạo tài khoản ");
            System.out.println("2. Đăng nhập ");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    loginAccount();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Chọn sai chức năng!");
                    showMenuAdmin();
            }
        }
    }

    private static void createAccount() {
        String fullName, username, password, rePassword, email, phone, createAt;
        boolean checkUsername, checkExistUsername, checkPassword, checkEmail, checkPhone;
        Pattern regexUsername = Pattern.compile("^[a-zA-Z0-9]{6,}$");
        Pattern regexPassword = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,}$");
        Pattern regexEmail = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Pattern regexPhone = Pattern.compile("^[0-9\\-\\+]{9,15}$");
        //Id
        System.out.print("Nhập id: ");
        long id = readNewId();
        //Fullname
        System.out.print("Nhập fullName: ");
        fullName = sc.nextLine();
        //Username
        do {
            System.out.print("Nhập username: ");
            username = sc.nextLine();
            checkUsername = regexUsername.matcher(username).find();
            checkExistUsername = checkExistUsername(username);
            if (!checkUsername) {
                System.out.println("Username không hợp lệ!");
            } else {
                if (checkExistUsername) {
                    System.out.println("Username đã tồn tại!");
                }
            }
        } while (checkExistUsername || !checkUsername);
        //Password
        do {
            System.out.print("Nhập password: ");
            password = sc.nextLine();
            System.out.print("Nhập lại password: ");
            rePassword = sc.nextLine();
            checkPassword = regexPassword.matcher(password).find();
            if (!checkPassword) {
                System.out.println("Password không hợp lệ!");
            } else {
                if (password.compareTo(rePassword) != 0) {
                    System.out.println("Password không khớp với nhau!");
                }
            }
        } while (!checkPassword);
        //Email
        do {
            System.out.print("Nhập email: ");
            email = sc.nextLine();
            checkEmail = regexEmail.matcher(email).find();
            if (!checkEmail) {
                System.out.println("Email không hợp lệ!");
            }
        } while (!checkEmail);
        //Phone
        do {
            System.out.print("Nhập số điện thoại: ");
            phone = sc.nextLine();
            checkPhone = regexPhone.matcher(phone).find();
            if (!checkPhone) {
                System.out.println("Số điện thoại không hợp lệ!");
            }
        } while (!checkPhone);
        //createAt
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        createAt = simpleDateFormat.format(date);

        Account account = new Account(id, fullName, username, password, email, phone, createAt);
        fileController.WriteAccountToFile("AccountAdmin.DAT",account);

        System.out.println("Tạo tài khoản thành công!");
    }

    private static long readNewId() {
        long id;
        while (true) {
            try {
                do {
                    id = Integer.parseInt(sc.nextLine());
                    if (checkExistId(id)) {
                        System.out.print("Id tồn tại! Nhập lại id: ");
                    }
                } while (checkExistId(id));
                return id;
            } catch (NumberFormatException e) {
                System.out.print("Nhập lại id: ");
            }
        }
    }

    private static boolean checkExistId(long id) {
        accountList = fileController.ReadAccountFromFile("AccountAdmin.DAT");
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkExistUsername(String username) {
        accountList = fileController.ReadAccountFromFile("AccountAdmin.DAT");
        for (int i = 0; i < accountList.size(); i++){
            if (accountList.get(i).getUsername().compareTo(username) == 0){
                return true;
            }
        }
        return false;
    }

    private static void loginAccount() {
        accountList = fileController.ReadAccountFromFile("AccountAdmin.DAT");
        String username, password;
        System.out.print("Nhập username: ");
        username = sc.nextLine();
        System.out.print("Nhập password: ");
        password = sc.nextLine();
        if (checkExistAccount(username,password)) {
            Account account = new Account();
            for (int i = 0; i < accountList.size(); i++) {
                if(accountList.get(i).getUsername().compareTo(username) == 0){
                    account = accountList.get(i);
                }
            }
            do {
                System.out.println("---------- Chức năng ----------");
                System.out.println("1. Quản lý danh sách Pet");
                System.out.println("2. Quản lý hóa đơn");
                System.out.println("3. Quản lý tài khoản");
                System.out.println("0. Quay lại");
                System.out.print("Chọn chức năng: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        petManagement();
                        break;
                    case 2:
                        customerManagement();
                        break;
                    case 3:
                        accountManagement(account);
                        break;
                    case 0:
                        return;
                }
            } while (true);
        } else {
            System.out.println("Tài khoản hoặc mật khẩu không tồn tại!");
        }
    }

    private static boolean checkExistAccount(String username, String password) {
        for (int i = 0; i < accountList.size(); i++){
            if (accountList.get(i).getUsername().compareTo(username) == 0 &&
                    accountList.get(i).getPassword().compareTo(password) == 0){
                return true;
            }
        }
        return false;
    }

    private static void accountManagement(Account account) {
        int choice;
        while (true) {
            System.out.println("----------- Chức năng -----------");
            System.out.println("1. Thông tin tài khoản");
            System.out.println("2. Thay đổi thông tin tài khoản");
            System.out.println("3. Danh sách tài khoản");
            System.out.println("4. Săp xếp danh sách tài khoản");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println(account);
                    break;
                case 2:
                    changeAccount(account);
                    break;
                case 3:
                    accountList = fileController.ReadAccountFromFile("AccountAdmin.DAT");
                    accountList.forEach(accounts -> System.out.println(accounts));
                    break;
                case 4:
                    sortAccount();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Chọn sai chức năng!");
            }
        }
    }

    private static void changeAccount(Account account) {
        do {
            System.out.println("---------- Thay đổi ----------");
            System.out.println("1. Fullname");
            System.out.println("2. Password");
            System.out.println("3. Phonenumber");
            System.out.println("4. Email");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    changeFullname(account);
                    break;
                case 2:
                    changePassword(account);
                    break;
                case 3:
                    changePhone(account);
                    break;
                case 4:
                    changeEmail(account);
                    break;
                case 0:
                    return;
            }
        } while (true);
    }

    private static void changeFullname(Account account) {
        String fullName;
        sc.nextLine();
        System.out.print("Nhập fullname: ");
        fullName = sc.nextLine();
        account.setFullName(fullName);
        fileController.UpdateAccountFile(accountList,"AccountAdmin.DAT");
        System.out.println("Thay đổi fullname thành công");
    }

    private static void changePassword(Account account) {
        String oldPassword = account.getPassword(), reOldPassword, password, rePassword;
        boolean checkPassword;
        sc.nextLine();
        Pattern regexPassword = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,}$");
        do {
            System.out.print("Nhập password cũ: ");
            reOldPassword = sc.nextLine();
            System.out.print("Nhập password mới: ");
            password = sc.nextLine();
            System.out.print("Nhập lại password mới: ");
            rePassword = sc.nextLine();
            checkPassword = regexPassword.matcher(password).find();
            if (reOldPassword.compareTo(oldPassword) != 0) {
                System.out.println("Password cũ không chính sác!");
            } else {
                if (!checkPassword) {
                    System.out.println("Password không hợp lệ!");
                } else {
                    if (rePassword.compareTo(password) != 0) {
                        System.out.println("Password không khớp với nhau!");
                    } else {
                        account.setPassword(password);
                        fileController.UpdateAccountFile(accountList,"AccountAdmin.DAT");
                        System.out.println("Thay đổi password thành công!");
                        return;
                    }
                }
            }
        } while (!checkPassword || rePassword.compareTo(password) != 0);
    }

    private static void changePhone(Account account) {
        String phone;
        boolean checkPhone;
        sc.nextLine();
        Pattern regexPhone = Pattern.compile("^[0-9\\-\\+]{9,15}$");
        do {
            System.out.print("Nhập số điện thoại: ");
            phone = sc.nextLine();
            checkPhone = regexPhone.matcher(phone).find();
            if (!checkPhone) {
                System.out.println("Số điện thoại không hợp lệ!");
            } else {
                account.setPhone(phone);
                fileController.UpdateAccountFile(accountList,"AccountAdmin.DAT");
                System.out.println("Thay đổi số điện thoại thành công!");
            }
        } while (!checkPhone);
    }

    private static void changeEmail(Account account) {
        String email;
        boolean checkEmail;
        sc.nextLine();
        Pattern regexEmail = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        do {
            System.out.print("Nhập email: ");
            email = sc.nextLine();
            checkEmail = regexEmail.matcher(email).find();
            if (!checkEmail) {
                System.out.println("Email không hợp lệ!");
            } else {
                account.setEmail(email);
                fileController.UpdateAccountFile(accountList,"AccountAdmin.DAT");
                System.out.println("Thay đổi email thành công!");
            }
        } while (!checkEmail);
    }
    private static void sortAccount() {
        do {
            System.out.println("---------- Chức năng----------");
            System.out.println("1. Sắp xếp danh sách tài khoản theo id");
            System.out.println("2. Sắp xếp danh sách tài khoản theo họ và tên");
            System.out.println("3. Sắp xếp danh sách tài khoản theo username");
            System.out.println("4. Sắp xếp danh sách tài khoản theo ngày tạo");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            int sortAccount = sc.nextInt();
            switch (sortAccount) {
                case 1:
                    accountList = fileController.ReadAccountFromFile("AccountAdmin.DAT");
                    for (int i = 0; i < accountList.size(); i++) {
                        for (int j = i + 1; j < accountList.size(); j++) {
                            if (accountList.get(i).getId() > accountList.get(j).getId()) {
                                Account temp = accountList.get(i);
                                accountList.set(i, accountList.get(j));
                                accountList.set(j, temp);
                            }
                        }
                    }
                    fileController.UpdateAccountFile(accountList, "AccountAdmin.DAT");
                    accountList.forEach(account -> System.out.println(account));
                    break;
                case 2:
                    accountList = fileController.ReadAccountFromFile("AccountAdmin.DAT");
                    Collections.sort(accountList, new SortAccByFullname());
                    fileController.UpdateAccountFile(accountList,"AccountAdmin.DAT");
                    accountList.forEach(account -> System.out.println(account));
                    break;
                case 3:
                    accountList = fileController.ReadAccountFromFile("AccountAdmin.DAT");
                    Collections.sort(accountList, new SortAccByUsername());
                    fileController.UpdateAccountFile(accountList,"AccountAdmin.DAT");
                    accountList.forEach(account -> System.out.println(account));
                    break;
                case 4:
                    accountList = fileController.ReadAccountFromFile("AccountAdmin.DAT");
                    Collections.sort(accountList, new SortAccByCreateAt());
                    fileController.UpdateAccountFile(accountList,"AccountAdmin.DAT");
                    accountList.forEach(account -> System.out.println(account));
                    break;
                case 0:
                    return;
            }
        } while (true);
    }

    private static void petManagement(){
        int choice;
        while (true) {
            System.out.println("----------- Chức năng -----------");
            System.out.println("1. Xem danh sách Pet");
            System.out.println("2. Thêm Pet vào danh sách");
            System.out.println("3. Xóa Pet trong danh sách");
            System.out.println("4. Sửa thông tin Pet trong danh sách");
            System.out.println("5. Sắp xếp danh sách Pet");
            System.out.println("6. Tìm kiếm Pet");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    petList = fileController.ReadPetFromFile("PetStore.DAT");
                    petList.forEach(pet -> System.out.println(pet));
                    break;
                case 2:
                    addPet();
                    break;
                case 3:
                    deletePet();
                    break;
                case 4:
                    changePet();
                    break;
                case 5:
                    sortPet();
                    break;
                case 6:
                    searchPet();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Chọn sai chức năng!");
            }
        }
    }

    private static void addPet() {
        //Id
        System.out.print("Nhập id: ");
        long id = readNewIdPet();
        //petName
        System.out.print("Nhập tên thú nuôi: ");
        String petName = sc.nextLine();
        //species
        System.out.print("Nhập giống loài thú nuôi: ");
        String species = sc.nextLine();
        //petGender
        System.out.print("Nhập giới tính thú nuôi: ");
        String petGender = sc.nextLine();
        //petAge
        System.out.print("Nhập tuổi thú nuôi: ");
        String petAge = sc.nextLine();
        //featherColor
        System.out.print("Nhập màu lông thú nuôi: ");
        String featherColor = sc.nextLine();
        //petPrice
        System.out.print("Nhập giá thú nuôi: ");
        long petPrice = sc.nextLong();

        Pet pet = new Pet(id, petName, species, petGender, petAge, featherColor, petPrice);
        fileController.WritePetToFile("PetStore.DAT",pet);
        System.out.println("Thêm pet vào danh sách thành công!");
    }

    private static boolean checkExistPetId(long id) {
        petList = fileController.ReadPetFromFile("PetStore.DAT");
        for (int i = 0; i < petList.size(); i++) {
            if (petList.get(i).getPetId() == id) {
                return true;
            }
        }
        return false;
    }

    private static long readNewIdPet() {
        long id;
        while (true) {
            try {
                do {
                    id = Integer.parseInt(sc.nextLine());
                    if (checkExistPetId(id)) {
                        System.out.print("Id tồn tại! Nhập lại ID: ");
                    }
                } while (checkExistPetId(id));
                return id;
            } catch (NumberFormatException e) {
                System.out.print("Nhập lại ID: ");
            }
        }
    }

    private static void deletePet() {
        petList = fileController.ReadPetFromFile("PetStore.DAT");
        long id;
        System.out.print("Nhập id của Pet cần xóa khỏi danh sách: ");
        do {
            id = sc.nextInt();
            if (!checkExistPetId(id)) {
                System.out.print("Id không tồn tại! Nhập lại id Pet cần xóa: ");
            }
        } while (!checkExistPetId(id));
        for (int i = 0; i < petList.size(); i++) {
            if (petList.get(i).getPetId() == id) {
                petList.remove(i);
            }
        }
        fileController.UpdatePetFile(petList,"PetStore.DAT");
        System.out.println("Xóa thành công!");
    }

    private static void changePet() {
        do {
            System.out.println("---------- Sửa ----------");
            System.out.println("1. Toàn bộ thông tin Pet");
            System.out.println("2. Từng thông tin Pet");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    long id1;
                    System.out.print("Nhập id của Pet cần sửa trong danh sách: ");
                    do {
                        id1 = sc.nextInt();
                        if (!checkExistPetId(id1)) {
                            System.out.print("Id không tồn tại! Nhập lại id Pet cần sửa: ");
                        }
                    } while (!checkExistPetId(id1));
                    petList = fileController.ReadPetFromFile("PetStore.DAT");
                    for (int i = 0; i < petList.size(); i++) {
                        if (petList.get(i).getPetId() == id1) {
                            sc.nextLine();
                            System.out.print("Nhập tên thú nuôi: ");
                            String petName = sc.nextLine();
                            petList.get(i).setPetName(petName);
                            System.out.print("Nhập giống loài thú nuôi: ");
                            String species = sc.nextLine();
                            petList.get(i).setSpecies(species);
                            System.out.print("Nhập giới tính thú nuôi: ");
                            String petGender = sc.nextLine();
                            petList.get(i).setPetGender(petGender);
                            System.out.print("Nhập tuổi thú nuôi: ");
                            String petAge = sc.nextLine();
                            petList.get(i).setPetAge(petAge);
                            System.out.print("Nhập màu lông thú nuôi: ");
                            String featherColor = sc.nextLine();
                            petList.get(i).setFeatherColor(featherColor);
                            System.out.print("Nhập giá thú nuôi: ");
                            long petPrice = sc.nextLong();
                            petList.get(i).setPetPrice(petPrice);
                            fileController.UpdatePetFile(petList, "PetStore.DAT");
                            System.out.println("Sửa thông tin Pet thành công!");
                        }
                    }
                    break;
                case 2:
                    System.out.println("---------- Sửa ----------");
                    System.out.println("1. Tên thú nuôi");
                    System.out.println("2. Giống loài thú nuôi");
                    System.out.println("3. Giới tính thú nuôi");
                    System.out.println("4. Tuổi thú nuôi");
                    System.out.println("5. Màu lông thú nuôi");
                    System.out.println("6. Giá thú nuôi");
                    System.out.print("Chọn chức năng: ");
                    int choiceP = sc.nextInt();
                    sc.nextLine();
                    long id2;
                    System.out.print("Nhập id của Pet cần sửa trong danh sách: ");
                    do {
                        id2 = sc.nextInt();
                        if (!checkExistPetId(id2)) {
                            System.out.print("Id không tồn tại! Nhập lại id Pet cần sửa: ");
                        }
                    } while (!checkExistPetId(id2));
                    switch (choiceP) {
                        case 1:
                            petList = fileController.ReadPetFromFile("PetStore.DAT");
                            for (int i = 0; i < petList.size(); i++) {
                                if (petList.get(i).getPetId() == id2) {
                                    sc.nextLine();
                                    System.out.print("Nhập tên thú nuôi: ");
                                    String petName = sc.nextLine();
                                    petList.get(i).setPetName(petName);
                                    fileController.UpdatePetFile(petList, "PetStore.DAT");
                                    System.out.println("Sửa thông tin Pet thành công!");
                                }
                            }
                            break;
                        case 2:
                            petList = fileController.ReadPetFromFile("PetStore.DAT");
                            for (int i = 0; i < petList.size(); i++) {
                                if (petList.get(i).getPetId() == id2) {
                                    sc.nextLine();
                                    System.out.print("Nhập giống loài thú nuôi: ");
                                    String species = sc.nextLine();
                                    petList.get(i).setSpecies(species);
                                    fileController.UpdatePetFile(petList, "PetStore.DAT");
                                    System.out.println("Sửa thông tin Pet thành công!");
                                }
                            }
                            break;
                        case 3:
                            petList = fileController.ReadPetFromFile("PetStore.DAT");
                            for (int i = 0; i < petList.size(); i++) {
                                if (petList.get(i).getPetId() == id2) {
                                    sc.nextLine();
                                    System.out.print("Nhập giới tính thú nuôi: ");
                                    String petGender = sc.nextLine();
                                    petList.get(i).setPetGender(petGender);
                                    fileController.UpdatePetFile(petList, "PetStore.DAT");
                                    System.out.println("Sửa thông tin Pet thành công!");
                                }
                            }
                            break;
                        case 4:
                            petList = fileController.ReadPetFromFile("PetStore.DAT");
                            for (int i = 0; i < petList.size(); i++) {
                                if (petList.get(i).getPetId() == id2) {
                                    sc.nextLine();
                                    System.out.print("Nhập tuổi thú nuôi: ");
                                    String petAge = sc.nextLine();
                                    petList.get(i).setPetAge(petAge);
                                    fileController.UpdatePetFile(petList, "PetStore.DAT");
                                    System.out.println("Sửa thông tin Pet thành công!");
                                }
                            }
                            break;
                        case 5:
                            petList = fileController.ReadPetFromFile("PetStore.DAT");
                            for (int i = 0; i < petList.size(); i++) {
                                if (petList.get(i).getPetId() == id2) {
                                    sc.nextLine();
                                    System.out.print("Nhập màu lông thú nuôi: ");
                                    String featherColor = sc.nextLine();
                                    petList.get(i).setFeatherColor(featherColor);
                                    fileController.UpdatePetFile(petList, "PetStore.DAT");
                                    System.out.println("Sửa thông tin Pet thành công!");
                                }
                            }
                            break;
                        case 6:
                            petList = fileController.ReadPetFromFile("PetStore.DAT");
                            for (int i = 0; i < petList.size(); i++) {
                                if (petList.get(i).getPetId() == id2) {
                                    sc.nextLine();
                                    System.out.print("Nhập giá thú nuôi: ");
                                    long petPrice = sc.nextLong();
                                    petList.get(i).setPetPrice(petPrice);
                                    fileController.UpdatePetFile(petList, "PetStore.DAT");
                                    System.out.println("Sửa thông tin Pet thành công!");
                                }
                            }
                            break;
                    }
                case 0:
                    return;
            }
        } while (true) ;
    }

    private static void sortPet() {
        do {
            System.out.println("---------- Chức năng----------");
            System.out.println("1. Sắp xếp danh sách Pet theo id");
            System.out.println("2. Sắp xếp danh sách Pet theo tên Pet");
            System.out.println("3. Sắp xếp danh sách Pet theo giá");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    petList = fileController.ReadPetFromFile("PetStore.DAT");
                    for (int i = 0; i < petList.size(); i++) {
                        for (int j = i + 1; j < petList.size(); j++) {
                            if (petList.get(i).getPetId() > petList.get(j).getPetId()) {
                                Pet temp = petList.get(i);
                                petList.set(i, petList.get(j));
                                petList.set(j, temp);
                            }
                        }
                    }
                    fileController.UpdatePetFile(petList, "PetStore.DAT");
                    petList.forEach(pet -> System.out.println(pet));
                    break;
                case 2:
                    petList = fileController.ReadPetFromFile("PetStore.DAT");
                    Collections.sort(petList, new SortPetByNamePet());
                    fileController.UpdatePetFile(petList, "PetStore.DAT");
                    petList.forEach(pet -> System.out.println(pet));
                    break;
                case 3:
                    petList = fileController.ReadPetFromFile("PetStore.DAT");
                    for (int i = 0; i < petList.size(); i++) {
                        for (int j = i + 1; j < petList.size(); j++) {
                            if (petList.get(i).getPetPrice() > petList.get(j).getPetPrice()) {
                                Pet temp = petList.get(i);
                                petList.set(i, petList.get(j));
                                petList.set(j, temp);
                            }
                        }
                    }
                    fileController.UpdatePetFile(petList, "PetStore.DAT");
                    petList.forEach(pet -> System.out.println(pet));
                    break;
                case 0:
                    return;
            }
        } while (true);
    }

    private static void searchPet() {
        do {
            System.out.println("---------- Chức năng----------");
            System.out.println("1. Tìm kiếm Pet theo tên");
            System.out.println("2. Tìm kiếm Pet theo loài");
            System.out.println("3. Tìm kiếm Pet theo màu lông");
            System.out.println("4. Tìm kiếm Pet theo giá");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            int choice = sc.nextInt();
            petList = fileController.ReadPetFromFile("PetStore.DAT");
            switch (choice) {
                case 1:
                    System.out.print("Nhập tên thú nuôi cần tìm: ");
                    sc.nextLine();
                    String namePet = sc.nextLine();
                    for (int i = 0; i < petList.size(); i++) {
                        if (petList.get(i).getPetName().contains(namePet)) {
                            System.out.println(petList.get(i));
                        }
                    }
                    break;
                case 2:
                    System.out.print("Nhập loài thú nuôi cần tìm: ");
                    sc.nextLine();
                    String species = sc.nextLine();
                    for (int i = 0; i < petList.size(); i++) {
                        if (petList.get(i).getSpecies().contains(species)) {
                            System.out.println(petList.get(i));
                        }
                    }
                    break;
                case 3:
                    System.out.print("Nhập màu lông thú nuôi cần tìm: ");
                    sc.nextLine();
                    String featherColor = sc.nextLine();
                    for (int i = 0; i < petList.size(); i++) {
                        if (petList.get(i).getFeatherColor().contains(featherColor)) {
                            System.out.println(petList.get(i));
                        }
                    }
                    break;
                case 4:
                    do {
                        System.out.println("---------- Giá----------");
                        System.out.println("1. Nhỏ hơn 5.000.000 VND");
                        System.out.println("2. 5.000.000 VND đến 10.000.000 VND");
                        System.out.println("4. 10.000.000 VND đến 20.000.000 VND");
                        System.out.println("3. Lớn hơn 20.000.000 VND");
                        System.out.println("0. Quay lại");
                        System.out.print("Chọn chức năng: ");
                        int find = sc.nextInt();
                        switch (find) {
                            case 1:
                                for (int i = 0; i < petList.size(); i++) {
                                    if (petList.get(i).getPetPrice() < 5000000) {
                                        System.out.println(petList.get(i));
                                    }
                                }
                                break;
                            case 2:
                                for (int i = 0; i < petList.size(); i++) {
                                    if (petList.get(i).getPetPrice() >= 5000000 && petList.get(i).getPetPrice() < 10000000) {
                                        System.out.println(petList.get(i));
                                    }
                                }
                                break;
                            case 3:
                                for (int i = 0; i < petList.size(); i++) {
                                    if (petList.get(i).getPetPrice() >= 10000000 && petList.get(i).getPetPrice() < 20000000) {
                                        System.out.println(petList.get(i));
                                    }
                                }
                                break;
                            case 4:
                                for (int i = 0; i < petList.size(); i++) {
                                    if (petList.get(i).getPetPrice() > 20000000) {
                                        System.out.println(petList.get(i));
                                    }
                                }
                                break;
                            case 0:
                                return;
                        }
                    } while (true);
                case 0:
                    return;
            }
        } while (true);
    }

    private static void customerManagement() {
        int choice;
        while (true) {
            System.out.println("----------- Chức năng -----------");
            System.out.println("1. Xem danh sách các hóa đơn");
            System.out.println("2. Tạo hóa đơn");
            System.out.println("3. Sắp xếp hóa đơn trong danh sách");
            System.out.println("4. Tìm kiếm hóa đơn");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    billList = fileController.ReadBillFromFile("Bill.DAT");
                    billList.forEach(bill -> System.out.println(bill));
                    break;
                case 2:
                    createBill();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Chọn sai chức năng!");
            }
        }
    }

    private static void createBill() {
        do {
            System.out.println("----------- Khách hàng -----------");
            System.out.println("1. Đã có thông tin");
            System.out.println("2. Chưa có thông tin");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1: {
                    System.out.print("Nhập id hóa đơn: ");
                    long billId = readNewBillId();
                    System.out.print("Nhập id khách hàng: ");
                    long customerId;
                    do {
                        customerId = sc.nextInt();
                        if (!checkExistCustomerId(customerId)) {
                            System.out.print("Id không tồn tại! Nhập lại id khách hàng: ");
                        }
                    } while (!checkExistPetId(customerId));

                    // Xóa Pet trong file PetStore, Thêm Pet vào file PetBill
                    System.out.print("Nhập id pet: ");
                    long petId;
                    do {
                        petId = sc.nextInt();
                        if (!checkExistPetId(petId)) {
                            System.out.print("Id không tồn tại! Nhập lại id Pet: ");
                        }
                    } while (!checkExistPetId(petId));
                    petList = fileController.ReadPetFromFile("PetStore.DAT");
                    for (int i = 0; i < petList.size(); i++) {
                        if (petList.get(i).getPetId() == petId) {
                            Pet pet = new Pet(petList.get(i).getPetId(), petList.get(i).getPetName(),
                                    petList.get(i).getSpecies(), petList.get(i).getPetGender(),
                                    petList.get(i).getPetAge(), petList.get(i).getFeatherColor(),
                                    petList.get(i).getPetPrice());
                            fileController.WritePetToFile("PetBill", pet);
                            petList.remove(i);
                        }
                    }
                    fileController.UpdatePetFile(petList,"PetStore.DAT");

                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String dateOfPurchase = simpleDateFormat.format(date);

                    Bill bill = new Bill(billId, customerId, petId, dateOfPurchase);
                    fileController.WriteBillToFile("Bill.DAT", bill);

                    System.out.println("Tạo hóa đơn thành công!");
                    break;
                }
                case 2: {
                    System.out.println("Nhập thông tin khách hàng:");
                    System.out.print("Nhập id khách hàng: ");
                    long customerId = readNewCustomerId();
                    System.out.print("Nhập tên khách hàng: ");
                    String customerName = sc.nextLine();
                    System.out.print("Nhập tuổi khách hàng: ");
                    int customerAge = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nhập giới tính khách hàng: ");
                    String customerGender = sc.nextLine();
                    System.out.print("Nhập địa chỉ khách hàng: ");
                    String customerAddress = sc.nextLine();
                    System.out.print("Nhập số điện thoại: ");
                    String customerPhone = sc.nextLine();

                    Customer customer = new Customer(customerId, customerName, customerAge, customerGender, customerAddress, customerPhone);
                    fileController.WriteCustomerToFile("Customer.DAT", customer);
                    System.out.println("Thêm khách hàng vào danh sách thành công!");

                    System.out.print("Nhập id hóa đơn: ");
                    long billId = readNewBillId();

                    // Xóa Pet trong file PetStore, Thêm Pet vào file PetBill
                    System.out.print("Nhập id pet: ");
                    long petId;
                    do {
                        petId = sc.nextInt();
                        if (!checkExistPetId(petId)) {
                            System.out.print("Id không tồn tại! Nhập lại id Pet: ");
                        }
                    } while (!checkExistPetId(petId));
                    petList = fileController.ReadPetFromFile("PetStore.DAT");
                    for (int i = 0; i < petList.size(); i++) {
                        if (petList.get(i).getPetId() == petId) {
                            Pet pet = new Pet(petList.get(i).getPetId(), petList.get(i).getPetName(),
                                    petList.get(i).getSpecies(), petList.get(i).getPetGender(),
                                    petList.get(i).getPetAge(), petList.get(i).getFeatherColor(),
                                    petList.get(i).getPetPrice());
                            fileController.WritePetToFile("PetBill", pet);
                            petList.remove(i);
                        }
                    }
                    fileController.UpdatePetFile(petList,"PetStore.DAT");

                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String dateOfPurchase = simpleDateFormat.format(date);

                    Bill bill = new Bill(billId, customerId, petId, dateOfPurchase);
                    fileController.WriteBillToFile("Bill.DAT", bill);

                    System.out.println("Tạo hóa đơn thành công!");
                    break;
                }
                case 0:
                    return;
            }
        } while (true);
    }

    private static boolean checkExistCustomerId(long id) {
        customerList = fileController.ReadCustomerFromFile("Customer.DAT");
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getCustomerId() == id) {
                return true;
            }
        }
        return false;
    }

    private static long readNewCustomerId() {
        long id;
        while (true) {
            try {
                do {
                    id = Integer.parseInt(sc.nextLine());
                    if (checkExistCustomerId(id)) {
                        System.out.print("Id tồn tại! Nhập lại ID: ");
                    }
                } while (checkExistCustomerId(id));
                return id;
            } catch (NumberFormatException e) {
                System.out.print("Nhập lại ID: ");
            }
        }
    }

    private static boolean checkExistBillId(long id) {
        billList = fileController.ReadBillFromFile("Bill.DAT");
        for (int i = 0; i < billList.size(); i++) {
            if (billList.get(i).getBillId() == id) {
                return true;
            }
        }
        return false;
    }

    private static long readNewBillId() {
        long id;
        while (true) {
            try {
                do {
                    id = Integer.parseInt(sc.nextLine());
                    if (checkExistBillId(id)) {
                        System.out.print("Id tồn tại! Nhập lại ID: ");
                    }
                } while (checkExistBillId(id));
                return id;
            } catch (NumberFormatException e) {
                System.out.print("Nhập lại ID: ");
            }
        }
    }
}