package wq.study.demo.designPattern.bridgePattern;


class Client {
	//桥接模式
	public static void main(String args[]) {
		Image image;
		ImageImp imp;
		image = new JPGImage();
		imp = new WindowsImp();
		image.setImageImp(imp);
		image.parseFile("小龙女");
	}
}
