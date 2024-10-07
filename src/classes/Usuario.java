package classes;

public class Usuario {
   private String nome; 

   private String email; 
   private String senha;
   private String cpf;
   private String estatus;
 
   
   private String data_nasc;
   private String tipo;
   //private String chamados;  


   private boolean resultUsuario;
   private boolean resultCadastro;
   
   static String usuarioSistema;
   
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public Boolean VerificarSenha(String senhaP, String senhaS){
        Boolean resultSenha;
        if(senhaP == senhaS){
            resultSenha = true; 

        }else{
            resultSenha = false; 
            
        }
        return resultSenha;
    }
    
    //public String getChamados() {
    //    return chamados;
    //}

    //public void setChamados(String chamados) {
    //    this.chamados = chamados;
    //}
    public Boolean VerificarUsuario(String email, String senha){
        Conexao banco = new Conexao();
        try{
            banco.AbrirConexao();
            banco.stmt = banco.con.createStatement();
            banco.resultset = banco.stmt.executeQuery("Select * from usuarios " + "WHERE email = '" + email + "'" + "AND senha = '" + senha + "'");
            //banco.resultset = banco.stmt.executeQuery("select * from usuarios " + "WHERE email = rickij.marinho@gmail.com" + "AND senha = 123");;;
            if(banco.resultset.next()){
                resultUsuario = true;
                System.out.println(banco.stmt.executeQuery("Select * from usuarios " + "WHERE email = '" + email + "'" + "AND senha = '" + senha + "'"));
                usuarioSistema = getNome();
            }else{
                resultUsuario = false;
                System.out.println("errado");
            }
            banco.FecharConexao();
        }catch(Exception ec){
            System.out.println("Erro ao verificar usuario" + ec.getMessage());
        }
        
        return resultUsuario;
    }
    public Boolean CadastrarUsuario(String nome, String email, String senha, String cpf, String data_nasc,String estatus, String tipo){
        Conexao banco = new Conexao();
        try{
            System.out.println("Teste");
            banco.AbrirConexao();
            banco.stmt = banco.con.createStatement();    
            String sql = "INSERT INTO usuarios (nome, email, senha, cpf, data_nasc,estatus,tipo) " + "VALUES ('"+ nome + "','" + email + "','" + senha + "','" + cpf + "','" + data_nasc + "','" + estatus + "','" + tipo + "')";
            System.out.println(sql);
            banco.stmt.execute("INSERT INTO usuarios (nome, email, senha, cpf, data_nasc,estatus,tipo) " + "VALUES ('"+ nome + "','" + email + "','" + senha + "','" + cpf + "','" + data_nasc + "','" + estatus + "','" + tipo + "')");
            resultCadastro = true;
            System.out.println("Usuario inserido com sucesso!");
            banco.FecharConexao();
        }catch(Exception ec){
            resultCadastro = false;
            System.out.println("erro ao inserir usuario ao banco de dados " + ec.getMessage());
        }
        return resultCadastro;

    }
}
