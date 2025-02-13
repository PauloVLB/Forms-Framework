package br.ufrn.FormsFramework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.FormsFramework.mapper.opcao.OpcaoMapper;
import br.ufrn.FormsFramework.mapper.opcao.OpcaoOutput;
import br.ufrn.FormsFramework.mapper.opcao.OpcoesListaUpdate;
import br.ufrn.FormsFramework.mapper.resposta.RespostaCreate;
import br.ufrn.FormsFramework.mapper.resposta.RespostaMapper;
import br.ufrn.FormsFramework.mapper.resposta.RespostaOutput;
import br.ufrn.FormsFramework.mapper.resposta.RespostaUpdate;
import br.ufrn.FormsFramework.model.Opcao;
import br.ufrn.FormsFramework.model.Resposta;
import br.ufrn.FormsFramework.service.RespostaService;

@RestController
@RequestMapping("/resposta")
public class RespostaController {
    
    @Autowired
    private RespostaService respostaService;
    
    @Autowired
    private RespostaMapper respostaMapper;

    @Autowired
    private OpcaoMapper opcaoMapper;

    @PostMapping
    public ResponseEntity<RespostaOutput> create(@RequestBody RespostaCreate respostaCreate){
        Resposta resposta = respostaMapper.toRespostaFromCreate(respostaCreate);
        Resposta respostaCriado = respostaService.create(resposta);
        RespostaOutput respostaOutput = respostaMapper.toRespostaOutput(respostaCriado);
        return new ResponseEntity<RespostaOutput>(respostaOutput, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaOutput> getById(@PathVariable Long id){
        Resposta resposta = respostaService.getById(id);
        RespostaOutput respostaOutput  = respostaMapper.toRespostaOutput(resposta);
        return new ResponseEntity<RespostaOutput>(respostaOutput, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<List<RespostaOutput>> getAtll(){
        List<Resposta> respostas = respostaService.getAll();
        List<RespostaOutput> respostasOutput = respostas
                    .stream()
                    .map(respostaMapper::toRespostaOutput)
                    .toList();
        return new ResponseEntity<List<RespostaOutput>>(respostasOutput, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaOutput> update(@PathVariable Long id, @RequestBody RespostaUpdate respostaUpdate){
        Resposta resposta = respostaMapper.toRespostaFromUpdate(respostaUpdate);
        Resposta respostaAtualizado = respostaService.update(id, resposta);
        RespostaOutput respostaOutput = respostaMapper.toRespostaOutput(respostaAtualizado);
        return new ResponseEntity<RespostaOutput>(respostaOutput, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        respostaService.delete(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAll() {
        respostaService.deleteAll();
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PatchMapping("/{idResposta}/addOpcaoMarcada/{idOpcao}")
    public ResponseEntity<OpcaoOutput> addOpcaoMarcada(@PathVariable Long idResposta, @PathVariable Long idOpcao){        
        Opcao opcaoMarcada = respostaService.addOpcaoMarcada(idResposta, idOpcao);
        OpcaoOutput opcaoOutput = opcaoMapper.toOpcaoOutput(opcaoMarcada);
        return new ResponseEntity<OpcaoOutput>(opcaoOutput, HttpStatus.OK);

    }

    @PutMapping("/{idResposta}/setOpcoesMarcadas")
    public ResponseEntity<List<OpcaoOutput>> setOpcoesMarcadas(@PathVariable Long idResposta, @RequestBody OpcoesListaUpdate opcoesMarcadas){
        List<Opcao> opcoes = respostaService.setOpcoesMarcadas(idResposta, opcoesMarcadas.opcoesMarcadasIds());
        List<OpcaoOutput> opcoesOutput = opcoes
                    .stream()
                    .map(opcaoMapper::toOpcaoOutput)
                    .toList();
        return new ResponseEntity<List<OpcaoOutput>>(opcoesOutput, HttpStatus.OK);
    }
}
