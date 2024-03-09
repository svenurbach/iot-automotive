<p>insurance works!</p>




<ul class="insurance">
  @for (contract of contracts; track contract.id) {
    <li>
      {{contract.id}}
    </li>
    <li>
      Policynumber; <span>{{contract.policyNumber}}</span>
    </li>
    <li>
      dd; <span>{{contract.deductible}}</span>
    </li>

  }
  @empty {
    <span>Task list is empty</span>
  }
</ul>
