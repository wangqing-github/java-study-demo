package wq.study.demo.designPattern.abstractFactoryPattern;

class SummerSkinFactory implements SkinFactory {
	public Button createButton() {
		return new SummerButton();
	}
 
	public TextField createTextField() {
		return new SummerTextField();
	}
 
	public ComboBox createComboBox() {
		return new SummerComboBox();
	}
}
