package br.com.quiz.service;

public class TemplateSimples {
	
	public static String montaTemplate(String nomeUsuario, Long codigoQuiz) {
		String template = "<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "  <head>\r\n"
				+ "    <meta charset=\"UTF-8\" />\r\n"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
				+ "    <title>ABA-QUIZ</title>\r\n"
				+ "  </head>\r\n"
				+ "  <body>\r\n"
				+ "    <table\r\n"
				+ "      cellpadding=\"10\"\r\n"
				+ "      cellspacing=\"10\"\r\n"
				+ "      class=\"es-content esd-footer-popover\"\r\n"
				+ "      align=\"center\"\r\n"
				+ "      style=\"background-color: #b09cb8\"\r\n"
				+ "    >\r\n"
				+ "      <tbody>\r\n"
				+ "        <tr style=\"text-align: center\">\r\n"
				+ "          <td>\r\n"
				+ "            <h4\r\n"
				+ "              style=\"\r\n"
				+ "                font-family: 'Gill Sans', 'Gill Sans MT', Calibri,\r\n"
				+ "                  'Trebuchet MS', sans-serif;\r\n"
				+ "                font-weight: lighter;\r\n"
				+ "                font-size: 18px;\r\n"
				+ "              \"\r\n"
				+ "            >\r\n"
				+ "              VOCÊ RECEBEU UM CONVITE DE\r\n"
				+ "              <br />\r\n"
				+ "              <span style=\"font-size: 28px; font-weight: bold\">\r\n"
				+ "                "+nomeUsuario+" </span\r\n"
				+ "              ><br />\r\n"
				+ "              PARA PARTICIPAR DE UM QUIZ\r\n"
				+ "            </h4>\r\n"
				+ "          </td>\r\n"
				+ "        </tr>\r\n"
				+ "        <tr>\r\n"
				+ "          <td align=\"center\" style=\"color: #f4f0f8\">\r\n"
				+ "            <h5\r\n"
				+ "              style=\"\r\n"
				+ "                text-align: center;\r\n"
				+ "                color: #f8f5fa;\r\n"
				+ "                font-family: 'Gill Sans', 'Gill Sans MT', Calibri,\r\n"
				+ "                  'Trebuchet MS', sans-serif;\r\n"
				+ "              \"\r\n"
				+ "            >\r\n"
				+ "              <b></b>&nbsp;<span style=\"font-size: 18px; font-weight: lighter\"\r\n"
				+ "                >VENHA PARTICIPAR DE UM QUIZ COM<br />\r\n"
				+ "                PERGUNTAS SELECIONADAS E PENSADAS PARA&nbsp;VOCÊ!</span\r\n"
				+ "              ><b></b>\r\n"
				+ "            </h5>\r\n"
				+ "          </td>\r\n"
				+ "        </tr>\r\n"
				+ "        <tr>\r\n"
				+ "          <td align=\"center\" style=\"color: #3d0c6b\">\r\n"
				+ "            <span\r\n"
				+ "              class=\"es-button-border es-button-border-1653361718585\"\r\n"
				+ "              style=\"\r\n"
				+ "                background: #741b47;\r\n"
				+ "                border-radius: 15px;\r\n"
				+ "                padding: 10px;\r\n"
				+ "                color: #ffffff;\r\n"
				+ "              \"\r\n"
				+ "              ><a\r\n"
				+ "                href=\"http://localhost:8080/quiz\"\r\n"
				+ "                class=\"es-button es-button-1653361718556\"\r\n"
				+ "                target=\"_blank\"\r\n"
				+ "                style=\"\r\n"
				+ "                  color: #ffffff;\r\n"
				+ "                  text-decoration: none;\r\n"
				+ "                  font-family: 'Gill Sans', 'Gill Sans MT', Calibri,\r\n"
				+ "                    'Trebuchet MS', sans-serif;\r\n"
				+ "                \"\r\n"
				+ "                >ACESSAR QUIZ</a\r\n"
				+ "              ></span\r\n"
				+ "            >\r\n"
				+ "          </td>\r\n"
				+ "        </tr>\r\n"
				+ "        <tr>\r\n"
				+ "            <td align=\"center\" style=\"color: #ffffff\">\r\n"
				+ "            <p\r\n"
				+ "              style=\"\r\n"
				+ "                margin: 10px;\r\n"
				+ "                padding: 10px;\r\n"
				+ "                color: #ffffff;\r\n"
				+ "                background-color: #4f235e;\r\n"
				+ "                font-family: 'Lucida Sans', 'Lucida Sans Regular',\r\n"
				+ "                  'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana,\r\n"
				+ "                  sans-serif;\r\n"
				+ "              \"\r\n"
				+ "            >"
				+ "                Ou acesse<br />www.abaquiz.com.br&nbsp; &nbsp;<br />e\r\n"
				+ "                digite o seu código de Quiz<br /><br /><span\r\n"
				+ "                  style=\"font-size: 24px\"\r\n"
				+ "                  >"+codigoQuiz+"</span\r\n"
				+ "                ><br />&nbsp; &nbsp; &nbsp;\r\n"
				+ "              </p>\r\n"
				+ "            </td>\r\n"
				+ "          </tr>\r\n"
				+ "      </tbody>\r\n"
				+ "    </table>\r\n"
				+ "  </body>\r\n"
				+ "</html>\r\n"
				+ "";
		
		
		return template;
	}
}
