package wq.study.demo.designPattern.abstractFactoryPattern;

interface SkinFactory {
	public Button createButton();
	public TextField createTextField();
	public ComboBox createComboBox();
}