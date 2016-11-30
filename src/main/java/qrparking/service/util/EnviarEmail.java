package qrparking.service.util;

public class EnviarEmail {
	public void sendEmail() throws EmailException {
		    
		   SimpleEmail email = new SimpleEmail();
		   //Utilize o hostname do seu provedor de email
		   System.out.println("alterando hostname...");
		   email.setHostName("smtp.gmail.com");
		   //Quando a porta utilizada não é a padrão (gmail = 465)
		   email.setSmtpPort(465);
		   //Adicione os destinatários
		   email.addTo("xxx@xxx.com", "Jose");
		   //Configure o seu email do qual enviará
		   email.setFrom("seuemail@seuprovedor.com", "Seu nome");
		   //Adicione um assunto
		   email.setSubject("Test message");
		   //Adicione a mensagem do email
		   email.setMsg("This is a simple test of commons-email");
		   //Para autenticar no servidor é necessário chamar os dois métodos abaixo
		   System.out.println("autenticando...");
		   email.setSSL(true);
		   email.setAuthentication("username", "senha");
		   System.out.println("enviando...");
		   email.send();
		   System.out.println("Email enviado!");
}
