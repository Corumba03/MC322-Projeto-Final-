package interfaceGrafica;

import Exceptions.*;
import pacotesViagem.Destino;
import pacotesViagem.Viagem;
import pacotesViagem.pacotes.Categorias;
import pacotesViagem.pacotes.PacoteBasico;
import pacotesViagem.pacotes.PacoteViagem;
import usuarios.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Tela extends JFrame{
    // Parâmetros básicos
    private static Tela instancia;
    public final static int largura = 1000;
    public final static int altura = 800;
    private final List<JComponent> componentes;
    JButton botaoCadastro;
    BotaoLogin botaoLogin;
    BotaoRegistrar botaoRegistrar;
    JTextField email;
    JLabel emailLabel;
    JPasswordField senha;
    JLabel senhaLabel;
    JPasswordField confirmacaoSenha;
    JLabel confirmacaoSenhaLabel;
    JTextField nome;
    JLabel nomeLabel;
    JTextField CPF;
    JLabel CPFLabel;

    private Tela() {
        // Criando a tela
        JFrame jFrame = new JFrame();
        setSize(largura, altura);
        setTitle("Plataforma de Turismo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        this.componentes = new ArrayList<>();

        telaLogin();
        setVisible(true);
    }

    public static Tela getInstance(int altura, int largura){
        if (instancia == null){
            instancia = new Tela();
        }
        return instancia;
    }
    private void telaLogin(){
        // Criando botão de login
        botaoLogin = BotaoLogin.getInstance(Fontes.arialTitulo);
        componentes.add(botaoLogin.getBotaoLogin());
        botaoLogin.getBotaoLogin().addActionListener(this::login);

        // Criando campo de email
        email = new JTextField();
        email.setBounds(largura/2 - 100, 250, 200, 40);
        componentes.add(email);


        emailLabel = new JLabel("E-mail:");
        emailLabel.setBounds(email.getX(), email.getY() - 40, 200, 50);
        componentes.add(emailLabel);

        // Criando um campo de senha
        senha = new JPasswordField();
        senha.setBounds(largura/2 - 100, 350, 200, 40);
        componentes.add(senha);


        senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(senha.getX(), senha.getY() - 40, 200, 30);
        componentes.add(senhaLabel);

        // Criando opção de se registrar
        JLabel cadastro = new JLabel("Ainda não se cadastrou?");
        cadastro.setBounds(largura/2 + 200, 270, 230, 50);
        componentes.add(cadastro);


        botaoCadastro = new JButton("Cadastre-se");
        botaoCadastro.setBounds(largura/2 + 210, 310, 210, 40);
        botaoCadastro.setForeground(new Color(239, 243, 239));
        botaoCadastro.setBackground(new Color(78, 162, 204));
        botaoCadastro.setFont(new Font("Arial", Font.BOLD, 30));
        componentes.add(botaoCadastro);
        botaoCadastro.addActionListener(this::telaCadastro);

        // Adicionando os componentes da tela
        pintarTela();
    }

    private void login(ActionEvent actionEvent) {
            //JOptionPane.showMessageDialog(null, "Login realizado", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            componentes.clear();
            this.getContentPane().removeAll();
            this.repaint();

            telaPrincipal();

    }

    private void telaCadastro(ActionEvent actionEvent) {
        this.getContentPane().removeAll();
        this.repaint();
        componentes.clear();

        // Criando campo de nome
        nome = new JTextField();
        nome.setBounds(largura/2 - 330, 200, 350, 40);
        componentes.add(nome);

        nomeLabel = new JLabel("Nome completo:");
        nomeLabel.setBounds(nome.getX(), nome.getY() - 30, 200, 30);
        componentes.add(nomeLabel);

        // Campo de CPF
        CPF = new JTextField();
        CPF.setBounds(largura/2 + 120, nome.getY(), 250, 40);
        componentes.add(CPF);

        CPFLabel = new JLabel("CPF (somente números):");
        CPFLabel.setBounds(CPF.getX(), CPF.getY() - 30, 300, 30);
        componentes.add(CPFLabel);


        // Criando campo de email
        email.setBounds(largura/2 - 330, 300, 350, 40);
        componentes.add(email);

        emailLabel.setText("Email:");
        emailLabel.setBounds(email.getX(), email.getY() - 40, 200, 50);
        componentes.add(emailLabel);

        // Campo de Login

        // Criando um campo de senha
        senha.setBounds(largura/2 - 330, 400, 250, 40);
        componentes.add(senha);

        senhaLabel.setBounds(senha.getX(), senha.getY() - 40, 200, 50);
        componentes.add(senhaLabel);

        // Criando um campo de confirmação de senha
        confirmacaoSenha = new JPasswordField();
        confirmacaoSenha.setBounds(CPF.getX(), senha.getY() , 250, 40);
        componentes.add(confirmacaoSenha);

        confirmacaoSenhaLabel = new JLabel("Confirmação de senha:");
        confirmacaoSenhaLabel.setBounds(confirmacaoSenha.getX(), confirmacaoSenha.getY() - 40, 300, 50);
        componentes.add(confirmacaoSenhaLabel);

        // Botão de criar registro
        botaoRegistrar = BotaoRegistrar.getInstance(largura, Fontes.arialTitulo);
        componentes.add(botaoRegistrar.getBotaoRegistrar());
        botaoRegistrar.getBotaoRegistrar().addActionListener(this::criarRegistro);


        // Adicionando os componentes da tela
        pintarTela();
    }
    private void criarRegistro(ActionEvent actionEvent) {
        String[] usuarioEmail = email.getText().split("@");
        char[] senhaCarac = senha.getPassword();
        char[] confirmacaoSenhaCarac = confirmacaoSenha.getPassword();
        try {
            if (nome.getText().isBlank() ||
                email.getText().isBlank() ||
                CPF.getText().isBlank() ||
                senhaCarac.length == 0 ||
                confirmacaoSenhaCarac.length == 0){
                throw new CampoVazioException("Preencha todos os campos");
            }
            if(!email.getText().contains("@") || !usuarioEmail[1].equals("gmail.com")){
                throw new EmailInvalidoException("Email inválido");
            }
            if(CPF.getText().length() != 11 || !CPF.getText().matches("\\d+")){
                throw new CPFInvalidoException("CPF inválido");
            }
            if(senhaCarac.length < 8){
                throw new SenhaInvalidaException("A senha deve conter ao menos 8 caracteres");
            }
            if(!Arrays.equals(senhaCarac, confirmacaoSenhaCarac)){
                throw new SenhaIncorretaException("As senhas devem ser iguais");
            }

            // TODO integrar com o banco de dados
            Usuario novoUsuario = new Usuario(CPF.getText(), nome.getText(), senhaCarac, email.getText(), 0);
            JOptionPane.showMessageDialog(null, "Registro realizado com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            componentes.clear();
            this.getContentPane().removeAll();
            this.repaint();
            telaLogin();
        }catch (EmailInvalidoException | CampoVazioException | CPFInvalidoException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Mensagem", JOptionPane.WARNING_MESSAGE);
        }catch (SenhaInvalidaException | SenhaIncorretaException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Senha inválida", JOptionPane.WARNING_MESSAGE);
            senha.setText("");
            confirmacaoSenha.setText("");
        }
    }

    private void telaPrincipal(){
        JLabel titulo = new JLabel("Tela Principal");
        titulo.setBounds(largura/2 -  150, 80, 400, 50);
        titulo.setFont(Fontes.arialTitulo);
        add(titulo);

        JLabel bemVindo = new JLabel("Bem-vindo, ");
        bemVindo.setBounds(20, 10, 200, 30);
        bemVindo.setFont(Fontes.arial);
        add(bemVindo);

        JButton botaoLogout = new JButton("Logout");
        botaoLogout.setBounds(largura - 150, 10, 100, 30);
        botaoLogout.setFont(Fontes.arial);
        botaoLogout.addActionListener(this::logout);
        add(botaoLogout);

        PacoteBasico pacotePlaceholder = new PacoteBasico(Categorias.NENHUMA, 0);
        Destino destinoPlaceholder = new Destino("", "", "", Duration.ofDays(0), 0.0, "");
        Viagem viagemPlaceholder = new Viagem(destinoPlaceholder, "", 0.0);
        pacotePlaceholder.setViagem(viagemPlaceholder);

        int xInit = 100;
        int yInit = 200;

        for (int i = 1; i < 5; i++){
            for (int j = 1; j < 4; j++){
                BotaoPacote novoBotao = new BotaoPacote(j*xInit + (j-1)*180, i*yInit, pacotePlaceholder);
                add(novoBotao.getBotaoPacote());
            }

        }
        //BotaoPacote novoBotao = new BotaoPacote(100, 200, pacotePlaceholder);
        //add(novoBotao.getBotaoPacote());
    }

    public static void telaPacote(PacoteViagem pacote){

    }

    private void logout(ActionEvent actionEvent) {
        this.getContentPane().removeAll();
        this.repaint();
        componentes.clear();
        telaLogin();
    }

    private void pintarTela(){
        for (JComponent componente : componentes){
            if (!(componente instanceof JButton)){
                componente.setFont(Fontes.arial);
            }
            add(componente);
        }
    }
}
