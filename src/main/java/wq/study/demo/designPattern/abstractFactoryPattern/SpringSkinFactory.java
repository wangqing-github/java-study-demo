package wq.study.demo.designPattern.abstractFactoryPattern;

class SpringSkinFactory implements SkinFactory {
	public Button createButton() {
		return new SpringButton();
	}
 
	public TextField createTextField() {
		return new SpringTextField();
	}
 
	public ComboBox createComboBox() {
		return new SpringComboBox();
	}
}
