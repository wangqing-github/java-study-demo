package wq.study.demo.designPattern.factoryMethodPattern;

class DatabaseLogger implements Logger {
	public void writeLog() {
		System.out.println("数据库日志记录。");
	}
}