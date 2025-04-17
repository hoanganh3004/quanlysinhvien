// Lớp SinhVien đại diện cho một sinh viên với các thông tin cơ bản
public class Student {
    // Thuộc tính private để lưu trữ thông tin sinh viên
    private int id;              // ID của sinh viên, tự động tăng
    private String masv;         // Mã sinh viên, duy nhất
    private String hoten;        // Họ tên sinh viên
    private int tuoi;            // Tuổi của sinh viên (thêm trường mới)
    private String diachi;       // Địa chỉ sinh viên
    private double diemtrungbinh;// Điểm trung bình, từ 0 đến 10

    // Constructor để khởi tạo một đối tượng SinhVien
    public Student(int id, String masv, String hoten, int tuoi, String diachi, double diemtrungbinh) {
        this.id = id;              // Gán giá trị cho ID
        this.masv = masv;          // Gán giá trị cho mã sinh viên
        this.hoten = hoten;        // Gán giá trị cho họ tên
        this.tuoi = tuoi;          // Gán giá trị cho tuổi
        this.diachi = diachi;      // Gán giá trị cho địa chỉ
        this.diemtrungbinh = diemtrungbinh; // Gán giá trị cho điểm trung bình
    }

    // Getter cho ID
    public int getId() {
        return id; // Trả về ID của sinh viên
    }

    // Getter cho mã sinh viên
    public String getMasv() {
        return masv; // Trả về mã sinh viên
    }

    // Getter cho họ tên
    public String getHoten() {
        return hoten; // Trả về họ tên
    }

    // Getter cho tuổi
    public int getTuoi() {
        return tuoi; // Trả về tuổi
    }

    // Getter cho địa chỉ
    public String getDiachi() {
        return diachi; // Trả về địa chỉ
    }

    // Getter cho điểm trung bình
    public double getDiemtrungbinh() {
        return diemtrungbinh; // Trả về điểm trung bình
    }

    // Setter cho họ tên để cập nhật thông tin
    public void setHoten(String hoten) {
        this.hoten = hoten; // Cập nhật họ tên
    }

    // Setter cho tuổi để cập nhật thông tin
    public void setTuoi(int tuoi) {
        this.tuoi = tuoi; // Cập nhật tuổi
    }

    // Setter cho địa chỉ để cập nhật thông tin
    public void setDiachi(String diachi) {
        this.diachi = diachi; // Cập nhật địa chỉ
    }

    // Setter cho điểm trung bình để cập nhật thông tin
    public void setDiemtrungbinh(double diemtrungbinh) {
        this.diemtrungbinh = diemtrungbinh; // Cập nhật điểm trung bình
    }

    // Phương thức toString để chuyển thông tin sinh viên thành chuỗi CSV
    @Override
    public String toString() {
        // Trả về chuỗi dạng "id,masv,hoten,tuoi,diachi,diemtrungbinh"
        return id + "," + masv + "," + hoten + "," + tuoi + "," + diachi + "," + diemtrungbinh;
    }

    // Phương thức display để hiển thị thông tin sinh viên theo hàng
    public void display() {
        // In tất cả thông tin trên cùng một dòng, cách nhau bởi khoảng trắng
        System.out.println("ID: " + id + ",  Mã SV: " + masv + ",  Họ tên: " + hoten + ",  Tuổi: " + tuoi + ",  Địa chỉ: " + diachi + ",  Điểm TB: " + diemtrungbinh);
    }
}