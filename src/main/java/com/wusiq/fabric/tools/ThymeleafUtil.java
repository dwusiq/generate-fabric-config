/**
 * Copyright 2014-2020  the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.wusiq.fabric.tools;

import com.wusiq.fabric.config.configtx.ConfigtxYaml;
import com.wusiq.fabric.config.container.CaYaml;
import com.wusiq.fabric.config.container.CliYaml;
import com.wusiq.fabric.config.container.OrdererYaml;
import com.wusiq.fabric.config.container.PeerYaml;
import com.wusiq.fabric.config.crypto.CryptoYaml;
import org.apache.commons.lang3.tuple.Pair;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import java.util.Map;


public class ThymeleafUtil {

    public static final String SAMPLE_PEER_YML = "sample-peer-yml.tpl";
    public static final String SAMPLE_ORDERER_YML = "sample-orderer-yml.tpl";
    public static final String SAMPLE_CA_YML = "sample-ca-yml.tpl";
    public static final String SAMPLE_CONFIGTX_YML = "sample-configtx-yml.tpl";
    public static final String SAMPLE_CRYPTO_YML = "sample-crypto-yml.tpl";


    /**
     *
     */
    public final static TemplateEngine templateEngine = new TemplateEngine();

    static {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(1);
        templateResolver.setPrefix("/templates/");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);

        templateEngine.addTemplateResolver(templateResolver);
    }

    /**
     * @param tpl
     * @param varMap
     */
    public static String generate(String tpl, Map<String, Object> varMap) {
        final Context ctx = new Context(Locale.CHINA);
        ctx.setVariables(varMap);
        return templateEngine.process(tpl, ctx);
    }

    /**
     * @param tpl
     * @param array
     */
    public static String generate(String tpl, Pair<String, Object>... array) {
        return generate(tpl, PairUtil.toMap(array));
    }

    /**
     * @param tpl
     * @param varMap
     */
    public static void write(String tpl, Writer writer, Map<String, Object> varMap) {
        final Context ctx = new Context(Locale.CHINA);
        ctx.setVariables(varMap);
        templateEngine.process(tpl, ctx, writer);
    }

    /**
     * @param tpl
     * @param array
     * @return
     */
    public static void write(String tpl, Writer writer, Pair<String, Object>... array) {
        write(tpl, writer, PairUtil.toMap(array));
    }


    /**
     * @param nodeRoot
     * @param peerYaml
     * @throws IOException
     */
    public static void samplePeerConfig(Path nodeRoot, PeerYaml peerYaml, CliYaml cliYaml) throws IOException {
        String applicationYml = ThymeleafUtil.generate(
                ThymeleafUtil.SAMPLE_PEER_YML,
                Pair.of("peerYaml", peerYaml),
                Pair.of("cliYaml", cliYaml)
        );
        Files.write(nodeRoot.resolve("docker-compose-peer0-org1.yml"), applicationYml.getBytes(), StandardOpenOption.CREATE);
    }


    public static void sampleOrdererConfig(Path nodeRoot, OrdererYaml orderer) throws IOException {
        String applicationYml = ThymeleafUtil.generate(
                ThymeleafUtil.SAMPLE_ORDERER_YML,
                Pair.of("orderer", orderer)
        );
        Files.write(nodeRoot.resolve("docker-compose-orderer.yml"), applicationYml.getBytes(), StandardOpenOption.CREATE);
    }


    public static void sampleCaConfig(Path nodeRoot, CaYaml caYaml) throws IOException {
        String applicationYml = ThymeleafUtil.generate(
                ThymeleafUtil.SAMPLE_CA_YML,
                Pair.of("caYaml", caYaml)
        );
        Files.write(nodeRoot.resolve("docker-compose-ca-org1.yml"), applicationYml.getBytes(), StandardOpenOption.CREATE);
    }


    public static void sampleConfigtxConfig(Path nodeRoot, ConfigtxYaml configtxYaml) throws IOException {
        String applicationYml = ThymeleafUtil.generate(
                ThymeleafUtil.SAMPLE_CONFIGTX_YML,
                Pair.of("configtxYaml", configtxYaml)
        );
        Files.write(nodeRoot.resolve("configtx.yml"), applicationYml.getBytes(), StandardOpenOption.CREATE);
    }


    public static void sampleCryptoConfig(Path nodeRoot, CryptoYaml cryptoYaml) throws IOException {
        String applicationYml = ThymeleafUtil.generate(
                ThymeleafUtil.SAMPLE_CRYPTO_YML,
                Pair.of("crypto", cryptoYaml)
        );
        Files.write(nodeRoot.resolve("crypto-config.yaml"), applicationYml.getBytes(), StandardOpenOption.CREATE);
    }

}

