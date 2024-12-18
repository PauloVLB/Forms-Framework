package br.ufrn.DASH.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.DASH.mapper.quesito.QuesitoCreate;
import br.ufrn.DASH.mapper.quesito.QuesitoMapper;
import br.ufrn.DASH.mapper.quesito.QuesitoOutput;
import br.ufrn.DASH.mapper.secao.SecaoCreate;
import br.ufrn.DASH.mapper.secao.SecaoMapper;
import br.ufrn.DASH.mapper.secao.SecaoOutput;
import br.ufrn.DASH.mapper.secao.SecaoUpdate;
import br.ufrn.DASH.model.Quesito;
import br.ufrn.DASH.model.Secao;
import br.ufrn.DASH.service.SecaoService;


@RestController
@RequestMapping("/secao")
public class SecaoController {

    @Autowired
    private SecaoService secaoService;

    @Autowired
    private SecaoMapper secaoMapper;

    @Autowired
    private QuesitoMapper quesitoMapper;

    @PostMapping
    public ResponseEntity<SecaoOutput> create(@RequestBody SecaoCreate secaoCreate) {
        Secao secao = secaoMapper.toSecaoFromCreate(secaoCreate);
        Secao secaoCriado = secaoService.create(secao);
        SecaoOutput secaoOutput = secaoMapper.toSecaoOutput(secaoCriado);
        return new ResponseEntity<SecaoOutput>(secaoOutput, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecaoOutput> getById(@PathVariable Long id) {
        Secao secao = secaoService.getById(id);
        SecaoOutput secaoOutput = secaoMapper.toSecaoOutput(secao);
        return new ResponseEntity<SecaoOutput>(secaoOutput, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SecaoOutput>> getAll() {
        List<Secao> secoes = secaoService.getAll();
        List<SecaoOutput> secoesOutput = secoes
                .stream()
                .map(secaoMapper::toSecaoOutput)
                .toList();
        return new ResponseEntity<List<SecaoOutput>>(secoesOutput, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecaoOutput> update(@PathVariable Long id, @RequestBody SecaoUpdate secaoUpdate) {
        Secao secao = secaoMapper.toSecaoFromUpdate(secaoUpdate);
        Secao secaoAtualizado = secaoService.update(id, secao);
        SecaoOutput secaoOutput = secaoMapper.toSecaoOutput(secaoAtualizado);
        return new ResponseEntity<SecaoOutput>(secaoOutput, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        secaoService.delete(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAll() {
        secaoService.deleteAll();
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping("/{idSecao}/addSubSecao")
    public ResponseEntity<SecaoOutput> addSubSecao(@PathVariable Long idSecao, @RequestBody SecaoCreate secaoCreate) {
        Secao secaoNova = secaoMapper.toSecaoFromCreate(secaoCreate);
        Secao secaoCriada = secaoService.addSubSecao(idSecao, secaoNova);
        SecaoOutput secaoOutput = secaoMapper.toSecaoOutput(secaoCriada);
        return new ResponseEntity<SecaoOutput>(secaoOutput, HttpStatus.CREATED);
    }

    @PostMapping("/{idSecao}/addQuesito")
    public ResponseEntity<QuesitoOutput> addQuesito(@PathVariable Long idSecao, @RequestBody QuesitoCreate quesitoCreate) {
        Quesito quesitoNovo = quesitoMapper.toQuesitoFromCreate(quesitoCreate);
        Quesito quesitoCriado = secaoService.addQuesito(idSecao, quesitoNovo);
        QuesitoOutput quesitoOutput = quesitoMapper.toQuesitoOutput(quesitoCriado);
        return new ResponseEntity<QuesitoOutput>(quesitoOutput, HttpStatus.CREATED);
    }

    @GetMapping("/{idSecao}/quesitos")
    public ResponseEntity<List<QuesitoOutput>> getQuesitos(@PathVariable Long idSecao) {
        List<Quesito> quesitos = secaoService.getQuesitos(idSecao);
        List<QuesitoOutput> quesitosOutput = quesitos
                .stream()
                .map(quesitoMapper::toQuesitoOutput)
                .toList();
        return new ResponseEntity<List<QuesitoOutput>>(quesitosOutput, HttpStatus.OK);
    }

    @GetMapping("/{idSecao}/subSecoes")
    public ResponseEntity<List<SecaoOutput>> getSubSecoes(@PathVariable Long idSecao) {
        List<Secao> subSecoes = secaoService.getSubSecoes(idSecao);
        List<SecaoOutput> subSecoesOutput = subSecoes
                .stream()
                .map(secaoMapper::toSecaoOutput)
                .toList();
        return new ResponseEntity<List<SecaoOutput>>(subSecoesOutput, HttpStatus.OK);
    }
    
}
