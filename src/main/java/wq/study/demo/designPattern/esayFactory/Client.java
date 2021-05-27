package wq.study.demo.designPattern.esayFactory;

class Client {
	//简单工厂模式测试
	public static void main(String args[]) {
		Chart chart;
		//通过静态工厂方法创建产品
		chart = ChartFactory.getChart("histogram");
		chart.display();
	}
}
