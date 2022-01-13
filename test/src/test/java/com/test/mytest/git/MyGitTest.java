//package com.test.mytest.git;
//
//import org.apache.commons.io.FileUtils;
//import org.eclipse.jgit.api.Git;
//import org.eclipse.jgit.api.errors.GitAPIException;
//import org.eclipse.jgit.lib.Repository;
//import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
//import org.eclipse.jgit.transport.CredentialsProvider;
//import org.eclipse.jgit.transport.RefSpec;
//import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Paths;
//
//public class MyGitTest {
//    /**
//     * credentialsProvider
//     * @param userName
//     * @param password
//     * @return
//     */
//    public static CredentialsProvider createCredential(String userName, String password) {
//        return new UsernamePasswordCredentialsProvider(userName, password);
//    }
//
//    /**
//     *通过Git.cloneRepository 来clone远程仓库，如果需要凭证，则需要指定credentialsProvider
//     * @param repoUrl
//     * @param cloneDir
//     * @param provider
//     * @return
//     * @throws GitAPIException
//     */
//    public static Git fromCloneRepository(String repoUrl, String cloneDir, CredentialsProvider provider) throws GitAPIException, GitAPIException {
//        Git git = Git.cloneRepository()
//                .setCredentialsProvider(provider)
//                .setURI(repoUrl)
//                .setDirectory(new File(cloneDir)).call();
//        return git;
//    }
//
//    /**
//     * commit, 注意需要先add
//     * @param git
//     * @param message
//     * @param provider
//     * @throws GitAPIException
//     */
//    public static void commit(Git git, String message, CredentialsProvider provider) throws GitAPIException {
//        git.add().addFilepattern(".").call();
//        git.commit()
//                .setMessage(message)
//                .call();
//    }
//
//    /**
//     * push直接调用push, 需要指定credentialsProvider
//     * @param git
//     * @param provider
//     * @throws GitAPIException
//     * @throws IOException
//     */
//    public static void push(Git git, CredentialsProvider provider) throws GitAPIException, IOException {
//        push(git,null,provider);
//    }
//
//    public static void push(Git git, String branch, CredentialsProvider provider) throws GitAPIException, IOException, IOException {
//        if (branch == null) {
//            branch = git.getRepository().getBranch();
//        }
//        git.push()
//                .setCredentialsProvider(provider)
//                .setRemote("origin").setRefSpecs(new RefSpec(branch)).call();
//    }
//
//    /**
//     * 如果git已经clone了，直接读取
//     * @param dir
//     * @return
//     * @throws IOException
//     */
//    public static Repository getRepositoryFromDir(String dir) throws IOException {
//        return new FileRepositoryBuilder()
//                .setGitDir(Paths.get(dir, ".git").toFile())
//                .build();
//    }
//
//
//
//    /**
//     *测试1： 先clone仓库，然后修改，最后push
//     */
//    @Test
//    public void test() throws GitAPIException, IOException {
//        String yaml = "dependencies:\n" +
//                "- name: springboot-rest-demo\n" +
//                "  version: 0.0.5\n" +
//                "  repository: http://hub.hubHOST.com/chartrepo/ainote\n" +
//                "  alias: demo\n" +
//                "- name: exposecontroller\n" +
//                "  version: 2.3.82\n" +
//                "  repository: http://chartmuseum.jenkins-x.io\n" +
//                "  alias: cleanup\n";
//
//        CredentialsProvider provider = createCredential("zhangshengyang", "zsy123456");
//
//        String cloneDir = "C:\\Users\\HTDEV\\Desktop\\gittest";
//
//        Git git = fromCloneRepository("http://10.73.1.87/zhangshengyang/uploadtest.git", cloneDir, provider);
//        // 修改文件
//        FileUtils.writeStringToFile(Paths.get(cloneDir, "env", "requirements.yaml").toFile(), yaml, "utf-8");
//        // 提交
//        commit(git, "git test1", provider);
//        // push 到远程仓库
//        push(git, "master", provider);
//
//        git.clean().call();
//        git.close();
//    }
//
//    /**
//     *测试2： git已经clone了，直接读取,然后提交,最后再push
//     */
//    @Test
//    public void test2() throws IOException, GitAPIException {
//        Repository repository = getRepositoryFromDir("C:\\Users\\HTDEV\\Desktop\\gittest");
//        Git git = new Git(repository);
//        CredentialsProvider provider = createCredential("zhangshengyang", "zsy123456");
//        // 提交
//        commit(git, "git test2", provider);
//        // push 到远程仓库
//        push(git, "master", provider);
//
//    }
//
//}
